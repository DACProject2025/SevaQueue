import React, { useEffect, useState } from 'react';
import { mainApi } from '../services/api';
import { logInfo, logError } from '../services/loggerService';
import { useNavigate } from 'react-router-dom';

const CitizenDashboard = () => {
    const navigate = useNavigate();
    const [offices, setOffices] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOffices = async () => {
            try {
                const response = await mainApi.get('/offices');
                setOffices(response.data);
                logInfo('Fetched offices successfully');
            } catch (error) {
                console.error('Error fetching offices:', error);
                logError(`Error fetching offices: ${error.message}`);
            } finally {
                setLoading(false);
            }
        };

        fetchOffices();
    }, []);

    if (loading) return <div className="container" style={{ textAlign: 'center' }}>Loading...</div>;

    return (
        <div className="container fade-in">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h2 style={{ margin: 0 }}>Citizen Dashboard - Select an Office</h2>
                <button className="btn" onClick={() => navigate('/my-tokens')} style={{ background: 'transparent', border: '1px solid #8b5cf6' }}>
                    My Tokens
                </button>
            </div>
            <div className="dashboard-grid">
                {offices.map((office) => (
                    <div key={office.officeId} className="glass-card">
                        <h3>{office.officeName}</h3>
                        <p style={{ color: '#cbd5e1' }}>Location: {office.address || office.city}</p>
                        <p style={{ marginTop: '1rem', fontWeight: 'bold' }}>
                            Status: <span style={{ color: office.active ? '#4ade80' : '#f87171' }}>
                                {office.active ? 'Active' : 'Inactive'}
                            </span>
                        </p>
                        <button
                            className="btn"
                            style={{ marginTop: '1rem', width: '100%' }}
                            onClick={() => navigate(`/office/${office.officeId}`)}
                        >
                            View Services & Book Token
                        </button>
                    </div>
                ))}
            </div>
            {offices.length === 0 && <p style={{ textAlign: 'center', color: '#cbd5e1' }}>No offices found.</p>}
        </div>
    );
};

export default CitizenDashboard;
