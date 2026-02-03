import React, { useEffect, useState } from 'react';
import { mainApi } from '../services/api';
import { createOffice, registerStaff, fetchAllOffices, toggleOfficeStatus } from '../services/adminService';
import { useNavigate } from 'react-router-dom';
import { logInfo, logError } from '../services/loggerService';

const AddStaffForm = () => {
    const [staff, setStaff] = useState({ name: '', email: '', password: '', mobile: '' });
    const [showPassword, setShowPassword] = useState(false);
    const [msg, setMsg] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await registerStaff(staff);
            setMsg('Staff registered successfully!');
            setStaff({ name: '', email: '', password: '', mobile: '' });
        } catch (error) {
            const errorMessage = error.response?.data?.message || error.message || 'Failed to register staff.';
            setMsg(`Error: ${errorMessage}`);
            console.error(error);
        }
    };

    return (
        <div>
            {msg && <p style={{ color: msg.includes('success') ? '#4ade80' : '#f87171', marginBottom: '1rem' }}>{msg}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', marginBottom: '1rem' }}>
                    <input className="input-field" placeholder="Name" value={staff.name} onChange={e => setStaff({ ...staff, name: e.target.value })} required />
                    <input className="input-field" placeholder="Email" value={staff.email} onChange={e => setStaff({ ...staff, email: e.target.value })} required />
                    <input className="input-field" placeholder="Mobile" value={staff.mobile} onChange={e => setStaff({ ...staff, mobile: e.target.value })} required />
                    <div style={{ position: 'relative', width: '100%' }}>
                        <input
                            className="input-field"
                            type={showPassword ? "text" : "password"}
                            placeholder="Password"
                            value={staff.password}
                            onChange={e => setStaff({ ...staff, password: e.target.value })}
                            required
                            style={{ paddingRight: '3rem', width: '100%' }}
                        />
                        <button
                            type="button"
                            onClick={() => setShowPassword(!showPassword)}
                            style={{
                                position: 'absolute',
                                right: '0.75rem',
                                top: '50%',
                                transform: 'translateY(-50%)',
                                background: 'none',
                                border: 'none',
                                cursor: 'pointer',
                                color: '#cbd5e1',
                                fontSize: '1.1rem',
                                padding: '0.25rem',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                zIndex: 10
                            }}
                        >
                            {showPassword ? '👁️' : '👁️‍🗨️'}
                        </button>
                    </div>
                </div>
                <button className="btn" style={{ marginTop: '1rem' }}>Register Staff</button>
            </form>
        </div>
    );
};

const ServiceStatsCard = ({ service }) => {
    const [counters, setCounters] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadCounters();
    }, [service.serviceId]);

    const loadCounters = async () => {
        try {
            const res = await mainApi.get(`/counter/service/${service.serviceId}`);
            setCounters(res.data || []);
        } catch (error) {
            console.error('Failed to load counters:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="glass-card" style={{ marginBottom: '1rem' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <div>
                    <h4 style={{ margin: 0, color: '#8b5cf6' }}>{service.serviceName}</h4>
                    <p style={{ margin: '0.25rem 0 0 0', fontSize: '0.85rem', color: '#94a3b8' }}>
                        {service.description || 'No description'}
                    </p>
                </div>
                <div style={{
                    background: 'rgba(139, 92, 246, 0.1)',
                    padding: '0.5rem 1rem',
                    borderRadius: '8px',
                    border: '1px solid rgba(139, 92, 246, 0.3)'
                }}>
                    <p style={{ margin: 0, fontSize: '0.85rem', color: '#cbd5e1' }}>Counters</p>
                    <p style={{ margin: 0, fontSize: '1.5rem', fontWeight: '700', color: '#8b5cf6' }}>
                        {counters.length}
                    </p>
                </div>
            </div>

            {loading ? (
                <p style={{ color: '#94a3b8', fontSize: '0.9rem' }}>Loading counters...</p>
            ) : counters.length > 0 ? (
                <div style={{ marginTop: '1rem' }}>
                    <p style={{ fontSize: '0.9rem', fontWeight: '600', marginBottom: '0.5rem', color: '#cbd5e1' }}>
                        Assigned Counters:
                    </p>
                    <div style={{ display: 'grid', gap: '0.5rem' }}>
                        {counters.map(counter => (
                            <div
                                key={counter.counterId}
                                style={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    alignItems: 'center',
                                    padding: '0.75rem',
                                    background: 'rgba(255, 255, 255, 0.05)',
                                    borderRadius: '6px',
                                    border: '1px solid rgba(255, 255, 255, 0.1)'
                                }}
                            >
                                <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                                    <span style={{
                                        fontWeight: '700',
                                        color: '#3b82f6',
                                        fontSize: '1rem'
                                    }}>
                                        Counter #{counter.counterNumber}
                                    </span>
                                    <span style={{
                                        fontSize: '0.85rem',
                                        color: '#94a3b8'
                                    }}>
                                        Staff: {counter.staffName || `ID: ${counter.staffId}`}
                                    </span>
                                </div>
                                <span style={{
                                    padding: '0.25rem 0.75rem',
                                    borderRadius: '4px',
                                    fontSize: '0.75rem',
                                    fontWeight: '600',
                                    background: counter.status === 'OPEN' ? 'rgba(34, 197, 94, 0.2)' : 'rgba(239, 68, 68, 0.2)',
                                    color: counter.status === 'OPEN' ? '#22c55e' : '#ef4444',
                                    border: `1px solid ${counter.status === 'OPEN' ? 'rgba(34, 197, 94, 0.3)' : 'rgba(239, 68, 68, 0.3)'}`
                                }}>
                                    {counter.status}
                                </span>
                            </div>
                        ))}
                    </div>
                </div>
            ) : (
                <p style={{ color: '#94a3b8', fontSize: '0.9rem', fontStyle: 'italic' }}>
                    No counters assigned to this service yet
                </p>
            )}
        </div>
    );
};

const AdminDashboard = () => {
    const [offices, setOffices] = useState([]);
    const [services, setServices] = useState([]);
    const [selectedOffice, setSelectedOffice] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newOffice, setNewOffice] = useState({ officeName: '', address: '', city: '', state: '', openTime: '08:00', closeTime: '23:00' });
    const navigate = useNavigate();

    useEffect(() => {
        loadOffices();
    }, []);

    useEffect(() => {
        if (selectedOffice) {
            loadServices(selectedOffice);
        }
    }, [selectedOffice]);

    const loadOffices = async () => {
        try {
            const res = await fetchAllOffices();
            setOffices(res.data);
            if (res.data.length > 0 && !selectedOffice) {
                setSelectedOffice(res.data[0].officeId);
            }
        } catch (error) {
            console.error('Failed to load offices:', error);
        }
    };

    const loadServices = async (officeId) => {
        try {
            const res = await mainApi.get(`/services/office/${officeId}`);
            setServices(res.data || []);
        } catch (error) {
            console.error('Failed to load services:', error);
            setServices([]);
        }
    };

    const handleCreate = async (e) => {
        e.preventDefault();
        try {
            const response = await createOffice(newOffice);
            console.log('Office created:', response.data);
            setShowForm(false);
            setNewOffice({ officeName: '', address: '', city: '', state: '', openTime: '08:00', closeTime: '23:00' });
            loadOffices();
            logInfo('Office created successfully');
            alert('Office created successfully!');
        } catch (error) {
            console.error('Failed to create office:', error);
            const errorMsg = error.response?.data?.message || error.message || 'Failed to create office';
            alert(`Error: ${errorMsg}`);
            logError('Failed to create office: ' + errorMsg);
        }
    };

    return (
        <div className="container fade-in">
            <h2 style={{ marginBottom: '2rem' }}>Admin Dashboard</h2>

            {/* Office Management Section */}
            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <h3>Manage Offices</h3>
                <p style={{ marginBottom: '1rem', color: '#cbd5e1' }}>Create, update, or deactivate offices.</p>
                <button className="btn" onClick={() => setShowForm(!showForm)}>
                    {showForm ? 'Cancel' : 'Create New Office'}
                </button>
            </div>

            {/* Staff Registration Section */}
            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <h3>Manage Staff</h3>
                <p style={{ marginBottom: '1rem', color: '#cbd5e1' }}>Register new staff members.</p>
                <AddStaffForm />
            </div>

            {/* Office Creation Form */}
            {showForm && (
                <div className="glass-card" style={{ marginBottom: '2rem', borderColor: '#8b5cf6' }}>
                    <form onSubmit={handleCreate}>
                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Office Name</label>
                                <input className="input-field" value={newOffice.officeName} onChange={e => setNewOffice({ ...newOffice, officeName: e.target.value })} required />
                            </div>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Address</label>
                                <input className="input-field" value={newOffice.address} onChange={e => setNewOffice({ ...newOffice, address: e.target.value })} required />
                            </div>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>City</label>
                                <input className="input-field" value={newOffice.city} onChange={e => setNewOffice({ ...newOffice, city: e.target.value })} required />
                            </div>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>State</label>
                                <input className="input-field" value={newOffice.state} onChange={e => setNewOffice({ ...newOffice, state: e.target.value })} required />
                            </div>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Open Time</label>
                                <input type="time" className="input-field" value={newOffice.openTime} onChange={e => setNewOffice({ ...newOffice, openTime: e.target.value })} required />
                            </div>
                            <div>
                                <label style={{ display: 'block', marginBottom: '0.5rem' }}>Close Time</label>
                                <input type="time" className="input-field" value={newOffice.closeTime} onChange={e => setNewOffice({ ...newOffice, closeTime: e.target.value })} required />
                            </div>
                        </div>
                        <button className="btn" style={{ marginTop: '1rem' }}>Save Office</button>
                    </form>
                </div>
            )}

            {/* Services & Counters Overview */}
            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <h3 style={{ marginBottom: '1rem' }}>Services & Counter Overview</h3>

                {/* Office Selector */}
                {offices.length > 0 && (
                    <div style={{ marginBottom: '1.5rem' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', fontWeight: '500' }}>
                            Select Office
                        </label>
                        <select
                            className="input-field"
                            value={selectedOffice || ''}
                            onChange={e => setSelectedOffice(e.target.value)}
                            style={{ maxWidth: '400px' }}
                        >
                            {offices.map(office => (
                                <option key={office.officeId} value={office.officeId}>
                                    {office.officeName} - {office.city}
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                {/* Services List */}
                {services.length > 0 ? (
                    <div>
                        <p style={{ fontSize: '0.9rem', color: '#94a3b8', marginBottom: '1rem' }}>
                            Showing {services.length} service{services.length !== 1 ? 's' : ''} for selected office
                        </p>
                        {services.map(service => (
                            <ServiceStatsCard key={service.serviceId} service={service} />
                        ))}
                    </div>
                ) : selectedOffice ? (
                    <p style={{ color: '#94a3b8', fontStyle: 'italic' }}>
                        No services found for this office. Create services in the office management page.
                    </p>
                ) : (
                    <p style={{ color: '#94a3b8', fontStyle: 'italic' }}>
                        No offices available. Create an office first.
                    </p>
                )}
            </div>

            {/* Existing Offices */}
            <h3>Existing Offices</h3>
            <div className="dashboard-grid">
                {offices.map((office) => (
                    <div key={office.officeId} className="glass-card">
                        <h4>{office.officeName}</h4>
                        <p style={{ fontSize: '0.9rem', color: '#cbd5e1' }}>{office.address}</p>
                        <p style={{ fontSize: '0.85rem', color: '#94a3b8' }}>{office.city}, {office.state}</p>
                        <p style={{ marginTop: '0.5rem', fontSize: '0.9rem', color: office.active ? '#4ade80' : '#f87171' }}>
                            {office.active ? '✓ Active' : '✗ Inactive'}
                        </p>
                        <div style={{ display: 'flex', gap: '0.5rem', marginTop: '1rem' }}>
                            <button
                                className="btn"
                                style={{ flex: 1, fontSize: '0.8rem', padding: '0.5rem' }}
                                onClick={() => navigate(`/admin/office/${office.officeId}`)}
                            >
                                Manage
                            </button>
                            <button
                                className="btn"
                                style={{
                                    flex: 1,
                                    fontSize: '0.8rem',
                                    padding: '0.5rem',
                                    background: office.active ? '#f87171' : '#4ade80'
                                }}
                                onClick={async () => {
                                    try {
                                        await toggleOfficeStatus(office.officeId);
                                        loadOffices();
                                    } catch (e) {
                                        alert('Failed to update status');
                                    }
                                }}
                            >
                                {office.active ? 'Deactivate' : 'Activate'}
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AdminDashboard;
