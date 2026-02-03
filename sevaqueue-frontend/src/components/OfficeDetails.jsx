import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { mainApi } from '../services/api';
import { logInfo, logError } from '../services/loggerService';
import { fetchServicesByOffice, generateToken } from '../services/tokenService';

const OfficeDetails = () => {
    const { id } = useParams();
    const [office, setOffice] = useState(null);
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const officeRes = await mainApi.get(`/offices/${id}`);
                setOffice(officeRes.data);

                const servicesRes = await fetchServicesByOffice(id);
                setServices(servicesRes.data);

                logInfo(`Fetched details for office ${id}`);
            } catch (error) {
                console.error('Error fetching details:', error);
                logError(`Error fetching details for office ${id}: ${error.message}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    const handleGenerateToken = async (serviceId) => {
        try {
            const res = await generateToken(serviceId);
            setMsg(`Token generated successfully! Your Token Number is: ${res.data.tokenNumber}. An SMS notification has been sent to your registered mobile number.`);
            logInfo(`Token generated for service ${serviceId}`);
        } catch (error) {
            console.error(error);
            const errorMessage = error.response?.data?.message || 'Failed to generate token.';
            setMsg(errorMessage);
            logError(`Token generation failed: ${errorMessage}`);
        }
    };

    if (loading) return <div className="container" style={{ textAlign: 'center' }}>Loading...</div>;
    if (!office) return <div className="container" style={{ textAlign: 'center' }}>Office not found.</div>;

    return (
        <div className="container fade-in">
            <button className="btn" onClick={() => navigate('/dashboard')} style={{ marginBottom: '2rem', padding: '0.5rem 1rem', background: 'transparent', border: '1px solid #8b5cf6' }}>
                &larr; Back to Dashboard
            </button>
            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <h2 style={{ marginBottom: '1.5rem' }}>{office.officeName}</h2>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '2rem' }}>
                    <div>
                        <p style={{ color: '#cbd5e1', marginBottom: '0.5rem' }}>Address</p>
                        <p style={{ fontSize: '1.1rem' }}>{office.address}</p>
                    </div>
                    <div>
                        <p style={{ color: '#cbd5e1', marginBottom: '0.5rem' }}>Status</p>
                        <span style={{ color: office.active ? '#4ade80' : '#f87171', fontWeight: 'bold' }}>
                            {office.active ? 'Active' : 'Inactive'}
                        </span>
                    </div>
                </div>
            </div>

            {msg && (
                <div className="glass-card" style={{ marginBottom: '2rem', background: 'rgba(74, 222, 128, 0.1)', borderColor: '#4ade80' }}>
                    <h3 style={{ margin: 0, textAlign: 'center', color: '#4ade80' }}>{msg}</h3>
                </div>
            )}

            <h3 style={{ marginBottom: '1.5rem' }}>Available Services</h3>
            <div className="dashboard-grid">
                {services.map(service => (
                    <div key={service.serviceId} className="glass-card">
                        <h4>{service.serviceName}</h4>
                        <p style={{ color: '#cbd5e1', fontSize: '0.9rem' }}>{service.description}</p>
                        <button
                            className="btn"
                            style={{ marginTop: '1rem', width: '100%' }}
                            onClick={() => handleGenerateToken(service.serviceId)}
                        >
                            Generate Token
                        </button>
                    </div>
                ))}
            </div>
            {services.length === 0 && <p style={{ color: '#cbd5e1' }}>No services available at this office.</p>}
        </div>
    );
};

export default OfficeDetails;
