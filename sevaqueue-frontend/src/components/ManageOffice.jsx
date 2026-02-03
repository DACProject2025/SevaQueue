import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { mainApi } from '../services/api';
import { createService, createCounter, deactivateService, deactivateOffice, getAllStaff } from '../services/adminService';
import { logInfo, logError } from '../services/loggerService';
import { fetchServicesByOffice } from '../services/tokenService';

const ManageOffice = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [office, setOffice] = useState(null);
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showServiceForm, setShowServiceForm] = useState(false);

    // New Service State
    const [newService, setNewService] = useState({ serviceName: '', description: '', avgWaitTime: 15 });

    const [staffList, setStaffList] = useState([]);

    useEffect(() => {
        loadData();
    }, [id]);

    const loadData = async () => {
        try {
            const officeRes = await mainApi.get(`/offices/${id}`);
            const serviceRes = await fetchServicesByOffice(id);
            setOffice(officeRes.data);
            setServices(serviceRes.data);

            // Should also fetch Staff List for assignment
            try {
                const staffRes = await getAllStaff();
                setStaffList(staffRes.data);
            } catch (e) { console.error("Failed to fetch staff", e); }

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateService = async (e) => {
        e.preventDefault();
        try {
            await createService(id, newService);
            setShowServiceForm(false);
            setNewService({ serviceName: '', description: '', avgWaitTime: 15 });
            loadData(); // Refresh list
            logInfo('Service created');
        } catch (error) {
            logError('Failed to create service');
        }
    };

    const handleDeactivate = async () => {
        if (window.confirm('Are you sure you want to deactivate this office?')) {
            try {
                await deactivateOffice(id);
                navigate('/dashboard');
            } catch (error) {
                console.error(error);
            }
        }
    };

    if (loading) return <div>Loading...</div>;
    if (!office) return <div>Not Found</div>;

    return (
        <div className="container fade-in">
            <button className="btn" onClick={() => navigate('/dashboard')} style={{ marginBottom: '2rem', padding: '0.5rem 1rem', background: 'transparent', border: '1px solid #8b5cf6' }}>
                &larr; Back to Dashboard
            </button>

            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <h2>Manage: {office.officeName}</h2>
                    <button onClick={handleDeactivate} style={{ color: '#f87171', background: 'transparent', border: 'none', cursor: 'pointer' }}>
                        Deactivate Office
                    </button>
                </div>
                <p>Address: {office.address}</p>
            </div>

            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <h3>Services</h3>
                <button className="btn" onClick={() => setShowServiceForm(!showServiceForm)}>
                    {showServiceForm ? 'Cancel' : 'Add Service'}
                </button>
            </div>

            {showServiceForm && (
                <div className="glass-card" style={{ marginBottom: '2rem', borderColor: '#8b5cf6' }}>
                    <form onSubmit={handleCreateService}>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Service Name</label>
                        <input className="input-field" value={newService.serviceName} onChange={e => setNewService({ ...newService, serviceName: e.target.value })} required />

                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Description</label>
                        <input className="input-field" value={newService.description} onChange={e => setNewService({ ...newService, description: e.target.value })} required />

                        <button className="btn">Save Service</button>
                    </form>
                </div>
            )}

            <div className="dashboard-grid">
                {services.map(s => (
                    <div key={s.serviceId} className="glass-card">
                        <h4>{s.serviceName}</h4>
                        <p>{s.description}</p>

                        <div style={{ marginTop: '1rem', paddingTop: '1rem', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
                            <CounterManager serviceId={s.serviceId} staffList={staffList} />
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

const CounterManager = ({ serviceId, staffList }) => {
    const [counters, setCounters] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [newCounter, setNewCounter] = useState({ counterNumber: '', staffId: '' });

    useEffect(() => {
        loadCounters();
    }, [serviceId]);

    const loadCounters = async () => {
        try {
            const res = await mainApi.get(`/counter/service/${serviceId}`);
            setCounters(res.data);
        } catch (e) { console.error(e); }
    };

    const handleCreate = async (e) => {
        e.preventDefault();
        try {
            await createCounter({ ...newCounter, serviceId });
            setShowForm(false);
            setNewCounter({ counterNumber: '', staffId: '' });
            loadCounters();
            alert('Counter assigned successfully!');
        } catch (e) {
            console.error(e);
            alert('Failed to create/assign counter');
        }
    };

    const toggleStatus = async (counterId, currentStatus) => {
        try {
            const newStatus = currentStatus === 'OPEN' ? 'CLOSED' : 'OPEN';
            await mainApi.put(`/counter/${counterId}/status?status=${newStatus}`);
            loadCounters();
        } catch (e) {
            console.error(e);
            alert('Failed to update status');
        }
    };

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem' }}>
                <h5 style={{ margin: 0 }}>Counters</h5>
                <button className="btn" style={{ fontSize: '0.8rem', padding: '0.2rem 0.5rem' }} onClick={() => setShowForm(!showForm)}>
                    + Add
                </button>
            </div>

            {showForm && (
                <form onSubmit={handleCreate} style={{ marginBottom: '1rem', background: 'rgba(0,0,0,0.2)', padding: '0.5rem', borderRadius: '4px' }}>
                    <input
                        className="input-field"
                        placeholder="Number (e.g. 1)"
                        type="number"
                        value={newCounter.counterNumber}
                        onChange={e => setNewCounter({ ...newCounter, counterNumber: e.target.value })}
                        required
                        style={{ marginBottom: '0.5rem' }}
                    />
                    <select
                        className="input-field"
                        value={newCounter.staffId}
                        onChange={e => setNewCounter({ ...newCounter, staffId: e.target.value })}
                        required
                        style={{ marginBottom: '0.5rem' }}
                    >
                        <option value="">Select Staff</option>
                        {staffList.map(st => <option key={st.userId} value={st.userId}>{st.name} ({st.email})</option>)}
                    </select>
                    <button className="btn" style={{ fontSize: '0.8rem', width: '100%' }}>Create & Assign</button>
                </form>
            )}

            <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                {counters.map(c => (
                    <div key={c.counterId} style={{ padding: '0.5rem', background: 'rgba(0,0,0,0.2)', borderRadius: '4px', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <span style={{ fontWeight: 'bold' }}>#{c.counterNumber}</span>
                            <span style={{ fontSize: '0.75rem', color: '#cbd5e1' }}>{c.staffName || `Staff ID: ${c.staffId}`}</span>
                        </div>
                        <span style={{ fontSize: '0.8rem', color: c.status === 'OPEN' ? '#4ade80' : '#f87171' }}>{c.status}</span>
                        <button
                            className="btn"
                            style={{ fontSize: '0.7rem', padding: '0.2rem 0.5rem', background: c.status === 'OPEN' ? '#ef4444' : '#22c55e', border: 'none', height: 'auto', lineHeight: 1, marginLeft: '0.5rem' }}
                            onClick={() => toggleStatus(c.counterId, c.status)}
                        >
                            {c.status === 'OPEN' ? 'Close' : 'Open'}
                        </button>
                    </div>
                ))}
                {counters.length === 0 && <span style={{ fontSize: '0.8rem', color: '#cbd5e1' }}>No counters</span>}
            </div>
        </div>
    );
};

export default ManageOffice;
