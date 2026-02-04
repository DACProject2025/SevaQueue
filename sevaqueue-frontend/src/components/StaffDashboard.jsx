import React, { useState, useEffect } from 'react';
import { mainApi } from '../services/api';
import { callNextToken, updateTokenStatus, getTokensByService, updateCounterStatus } from '../services/staffService';
import { logInfo, logError } from '../services/loggerService';
import { useAuth } from '../context/AuthContext';

const StaffDashboard = () => {
    const { user } = useAuth();
    // Counter Assignment (Auto-loaded) - Support multiple counters
    const [assignedCounters, setAssignedCounters] = useState([]); // All assigned counters
    const [selectedCounter, setSelectedCounter] = useState(null); // Currently selected counter
    const [loading, setLoading] = useState(true);

    // Token Management
    const [currentToken, setCurrentToken] = useState(null);
    const [queue, setQueue] = useState([]);
    const [allTokens, setAllTokens] = useState([]);
    const [msg, setMsg] = useState('');

    // Load assigned counter on load or when user data becomes available
    useEffect(() => {
        if (user && user.userId) {
            loadAssignedCounters();
        }
    }, [user?.userId]);

    // Load queue when counter is selected
    useEffect(() => {
        if (selectedCounter) {
            loadQueue(selectedCounter.serviceId);
        }
    }, [selectedCounter]);

    const loadAssignedCounters = async () => {
        try {
            setLoading(true);

            if (!user || !user.userId) {
                console.warn('DEBUG: User or UserId missing from context', user);
                setMsg('User not logged in or ID missing');
                return;
            }
            const userId = user.userId;
            console.log('DEBUG: User loaded from AuthContext:', user);
            console.log('DEBUG: Fetching counters for Staff ID:', userId);

            // Fetch assigned counters
            const res = await mainApi.get(`/counter/staff/${userId}`);
            console.log('DEBUG: API Response for counters:', res.data);

            if (res.data && res.data.length > 0) {
                setAssignedCounters(res.data); // Store all counters
                setSelectedCounter(res.data[0]); // Default to first counter
                setMsg('');
            } else {
                console.warn('DEBUG: No counter found for this staff ID');
                setMsg('No counter assigned to you. Please contact admin.');
            }
        } catch (error) {
            console.error('Error loading counters:', error);
            setMsg('Failed to load assigned counters. Please contact admin.');
        } finally {
            setLoading(false);
        }
    };

    const loadQueue = async (serviceId) => {
        try {
            const res = await getTokensByService(serviceId);
            const allTokensData = res.data || [];
            setAllTokens(allTokensData);

            // Filter waiting tokens
            const waitingTokens = allTokensData.filter(t => t.status === 'WAITING');
            setQueue(waitingTokens);

            // Find active token (CALLED status)
            const activeToken = allTokensData.find(t => t.status === 'CALLED');
            if (activeToken) {
                setCurrentToken(activeToken);
            }
        } catch (e) {
            console.error('Error loading queue:', e);
        }
    };

    const handleCallNext = async () => {
        if (!selectedCounter) {
            setMsg('No counter selected.');
            return;
        }
        try {
            setMsg('Calling next token...');
            const res = await callNextToken(selectedCounter.serviceId, selectedCounter.counterId);
            if (res.data) {
                setCurrentToken(res.data);
                setMsg(`Token ${res.data.tokenNumber} Called!`);
                logInfo(`Token ${res.data.tokenNumber} called by counter ${selectedCounter.counterNumber}`);
                loadQueue(selectedCounter.serviceId);
            } else {
                setMsg('No tokens in queue.');
            }
        } catch (error) {
            setMsg('Failed to call next token. Queue might be empty.');
        }
    };

    const handleStatusUpdate = async (status) => {
        if (!currentToken) return;
        try {
            await updateTokenStatus(currentToken.tokenId, status);
            setMsg(`Token ${currentToken.tokenNumber} marked as ${status}`);
            setCurrentToken(null);
            loadQueue(selectedCounter.serviceId);
        } catch (e) {
            setMsg('Failed to update status');
        }
    };

    const changeCounterStatus = async (status) => {
        if (!selectedCounter) return;
        try {
            await updateCounterStatus(selectedCounter.counterId, status);
            setMsg(`Counter #${selectedCounter.counterNumber} is now ${status}`);
            // Update local state for selected counter
            setSelectedCounter({ ...selectedCounter, status });
            // Also update in the counters array
            setAssignedCounters(assignedCounters.map(c =>
                c.counterId === selectedCounter.counterId ? { ...c, status } : c
            ));
        } catch (error) {
            console.error(error);
            setMsg(`Failed to set counter to ${status}`);
        }
    };

    const handleCounterChange = (counterId) => {
        const counter = assignedCounters.find(c => c.counterId === parseInt(counterId));
        if (counter) {
            setSelectedCounter(counter);
            setCurrentToken(null); // Reset current token when switching counters
            setMsg('');
        }
    };

    if (loading) {
        return (
            <div className="container fade-in" style={{ textAlign: 'center', padding: '4rem' }}>
                <h2>Loading your workstation...</h2>
            </div>
        );
    }

    if (!assignedCounters || assignedCounters.length === 0) {
        return (
            <div className="container fade-in">
                <div className="glass-card" style={{ textAlign: 'center', padding: '3rem' }}>
                    <div style={{ marginBottom: '1rem', color: '#94a3b8', fontSize: '0.85rem', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '0.5rem', textAlign: 'left' }}>
                        <strong>DEBUG INFO:</strong><br />
                        • User ID: <span style={{ color: '#fff' }}>{user?.userId !== undefined ? user.userId : 'UNDEFINED'}</span><br />
                        • Role: <span style={{ color: '#fff' }}>{user?.role || 'MISSING'}</span><br />
                        • Email: <span style={{ color: '#fff' }}>{user?.email || 'MISSING'}</span><br />
                        • Token exists: <span style={{ color: user?.token ? '#4ade80' : '#f87171' }}>{user?.token ? 'YES' : 'NO'}</span>
                    </div>
                    <h2 style={{ color: '#f87171', marginBottom: '1rem' }}>No Counter Assigned</h2>
                    <p style={{ color: '#cbd5e1', fontSize: '1.1rem' }}>
                        You don't have a counter assigned yet. Please contact your administrator to assign you a counter.
                    </p>
                </div>
            </div>
        );
    }

    return (
        <div className="container fade-in">
            <h2 style={{ marginBottom: '2rem' }}>Staff Dashboard</h2>

            {/* Counter Selector - Show if multiple counters */}
            {assignedCounters.length > 1 && (
                <div className="glass-card" style={{ marginBottom: '2rem', background: 'rgba(139, 92, 246, 0.05)', borderColor: '#8b5cf6' }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', flex: 1 }}>
                            <span style={{ fontSize: '1.1rem', fontWeight: '600' }}>🔄 Switch Counter:</span>
                            <select
                                className="input-field"
                                value={selectedCounter?.counterId || ''}
                                onChange={(e) => handleCounterChange(e.target.value)}
                                style={{ maxWidth: '300px', padding: '0.6rem 1rem' }}
                            >
                                {assignedCounters.map(counter => (
                                    <option key={counter.counterId} value={counter.counterId}>
                                        Counter #{counter.counterNumber} - {counter.serviceName} ({counter.status})
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div style={{
                            padding: '0.5rem 1rem',
                            background: 'rgba(139, 92, 246, 0.2)',
                            borderRadius: '8px',
                            fontSize: '0.9rem',
                            fontWeight: '600',
                            color: '#a78bfa'
                        }}>
                            {assignedCounters.length} Counters Assigned
                        </div>
                    </div>
                </div>
            )}

            {/* Counter Assignment Info */}
            <div className="glass-card" style={{ marginBottom: '2rem' }}>
                <h3 style={{ marginBottom: '1rem' }}>
                    {assignedCounters.length > 1 ? 'Current Workstation' : 'Your Workstation'}
                </h3>
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem' }}>
                    <div style={{ padding: '1rem', background: 'rgba(139, 92, 246, 0.1)', borderRadius: '8px', border: '1px solid rgba(139, 92, 246, 0.3)' }}>
                        <p style={{ fontSize: '0.85rem', color: '#cbd5e1', marginBottom: '0.25rem' }}>Office</p>
                        <p style={{ fontSize: '1.1rem', fontWeight: '600', color: '#8b5cf6' }}>{selectedCounter.officeName}</p>
                    </div>
                    <div style={{ padding: '1rem', background: 'rgba(59, 130, 246, 0.1)', borderRadius: '8px', border: '1px solid rgba(59, 130, 246, 0.3)' }}>
                        <p style={{ fontSize: '0.85rem', color: '#cbd5e1', marginBottom: '0.25rem' }}>Service</p>
                        <p style={{ fontSize: '1.1rem', fontWeight: '600', color: '#3b82f6' }}>{selectedCounter.serviceName}</p>
                    </div>
                    <div style={{ padding: '1rem', background: 'rgba(34, 197, 94, 0.1)', borderRadius: '8px', border: '1px solid rgba(34, 197, 94, 0.3)' }}>
                        <p style={{ fontSize: '0.85rem', color: '#cbd5e1', marginBottom: '0.25rem' }}>Counter Number</p>
                        <p style={{ fontSize: '1.1rem', fontWeight: '600', color: '#22c55e' }}>#{selectedCounter.counterNumber}</p>
                    </div>
                    <div style={{ padding: '1rem', background: selectedCounter.status === 'OPEN' ? 'rgba(34, 197, 94, 0.1)' : 'rgba(239, 68, 68, 0.1)', borderRadius: '8px', border: `1px solid ${selectedCounter.status === 'OPEN' ? 'rgba(34, 197, 94, 0.3)' : 'rgba(239, 68, 68, 0.3)'}` }}>
                        <p style={{ fontSize: '0.85rem', color: '#cbd5e1', marginBottom: '0.25rem' }}>Status</p>
                        <p style={{ fontSize: '1.1rem', fontWeight: '600', color: selectedCounter.status === 'OPEN' ? '#22c55e' : '#ef4444' }}>{selectedCounter.status}</p>
                    </div>
                </div>
            </div>

            {/* Action Section */}
            <div className="glass-card" style={{ textAlign: 'center' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem', alignItems: 'center' }}>
                    <h4 style={{ margin: 0 }}>Token Management</h4>
                    <div>
                        <button
                            className="btn"
                            style={{ background: '#ef4444', fontSize: '0.8rem', padding: '0.5rem 1rem' }}
                            onClick={() => changeCounterStatus('CLOSED')}
                        >
                            Close Counter
                        </button>
                        <button
                            className="btn"
                            style={{ background: '#22c55e', fontSize: '0.8rem', padding: '0.5rem 1rem', marginLeft: '0.5rem' }}
                            onClick={() => changeCounterStatus('OPEN')}
                        >
                            Open Counter
                        </button>
                    </div>
                </div>

                {msg && <p style={{ color: '#facc15', marginBottom: '1rem' }}>{msg}</p>}

                {currentToken ? (
                    <div style={{ padding: '2rem', border: '2px solid #8b5cf6', borderRadius: '1rem', marginBottom: '2rem', background: 'rgba(139, 92, 246, 0.05)' }}>
                        <p style={{ fontSize: '0.9rem', color: '#cbd5e1' }}>Currently Serving</p>
                        <h1 style={{ fontSize: '4rem', margin: '1rem 0', color: '#8b5cf6' }}>{currentToken.tokenNumber}</h1>
                        <div style={{ display: 'flex', gap: '1rem', justifyContent: 'center' }}>
                            <button className="btn" style={{ background: '#4ade80', padding: '0.75rem 2rem' }} onClick={() => handleStatusUpdate('SERVED')}>
                                ✓ Mark Served
                            </button>
                            <button className="btn" style={{ background: '#f87171', padding: '0.75rem 2rem' }} onClick={() => handleStatusUpdate('MISSED')}>
                                ✗ Mark Missed
                            </button>
                        </div>
                    </div>
                ) : (
                    <div style={{ padding: '2rem' }}>
                        <p style={{ marginBottom: '1rem', fontSize: '1.1rem' }}>Ready to serve next customer?</p>
                        <button
                            className="btn"
                            style={{ fontSize: '1.5rem', padding: '1rem 3rem', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' }}
                            onClick={handleCallNext}
                        >
                            📢 Call Next Token
                        </button>
                    </div>
                )}

                {/* Queue Status */}
                <div style={{ marginTop: '2rem', textAlign: 'left' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                        <h4 style={{ margin: 0 }}>Queue Status</h4>
                        <div style={{ display: 'flex', gap: '1rem', fontSize: '0.9rem' }}>
                            <span style={{ color: '#22c55e' }}>⏳ Waiting: {queue.length}</span>
                            <span style={{ color: '#8b5cf6' }}>📞 Active: {currentToken ? 1 : 0}</span>
                            <span style={{ color: '#cbd5e1' }}>📊 Total: {allTokens.length}</span>
                        </div>
                    </div>

                    {queue.length > 0 ? (
                        <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap', marginTop: '0.5rem' }}>
                            {queue.slice(0, 10).map(t => (
                                <span
                                    key={t.tokenId}
                                    style={{
                                        padding: '0.5rem 0.75rem',
                                        background: 'rgba(255,255,255,0.1)',
                                        borderRadius: '6px',
                                        fontSize: '0.9rem',
                                        fontWeight: '600'
                                    }}
                                >
                                    #{t.tokenNumber}
                                </span>
                            ))}
                            {queue.length > 10 && (
                                <span style={{ padding: '0.5rem 0.75rem', color: '#cbd5e1', fontSize: '0.9rem' }}>
                                    +{queue.length - 10} more
                                </span>
                            )}
                        </div>
                    ) : (
                        <p style={{ color: '#94a3b8', fontStyle: 'italic', marginTop: '0.5rem' }}>No tokens waiting in queue</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default StaffDashboard;
