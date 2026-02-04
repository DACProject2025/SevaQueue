import React, { useEffect, useState, useCallback } from 'react';
import { getMyTokens, getQueueStatus } from '../services/tokenService';
import { logInfo, logError } from '../services/loggerService';
import { useNavigate } from 'react-router-dom';

const TokenStatusRow = ({ token }) => {
    const [status, setStatus] = useState(null);
    const [notified, setNotified] = useState(false);

    const fetchStatus = useCallback(async () => {
        if (token.status !== 'WAITING' && token.status !== 'CALLED') return;
        try {
            const res = await getQueueStatus(token.tokenId);
            setStatus(res.data);

            // Notification logic
            if (res.data.userTurn && !notified) {
                if ("Notification" in window && Notification.permission === "granted") {
                    new Notification("It's your turn!", {
                        body: `Your token #${token.tokenNumber} for ${token.serviceName} is being called!`,
                        icon: '/logo192.png' // Use app logo if available
                    });
                }
                setNotified(true);
            }
        } catch (e) {
            console.error(e);
        }
    }, [token, notified]);

    useEffect(() => {
        fetchStatus();
        const interval = setInterval(fetchStatus, 10000); // Poll every 10 seconds
        return () => clearInterval(interval);
    }, [fetchStatus]);

    if (!status) return null;

    // Calculate estimated wait time
    const estimatedMinutes = status.waitingBeforeUser * status.avgServiceTime;
    const hours = Math.floor(estimatedMinutes / 60);
    const minutes = estimatedMinutes % 60;
    const estimatedTimeStr = hours > 0
        ? `~${hours}h ${minutes}m`
        : `~${minutes} min`;

    return (
        <div style={{ marginTop: '1rem', padding: '0.75rem', background: 'rgba(139, 92, 246, 0.1)', borderRadius: '8px', borderLeft: '3px solid #8b5cf6' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '0.5rem' }}>
                <span>Currently Serving: <strong style={{ color: '#8b5cf6' }}>{status.currentServingTokenNumber}</strong></span>
                <span>Wait: <strong style={{ color: '#8b5cf6' }}>{status.waitingBeforeUser}</strong> ahead</span>
            </div>
            {status.waitingBeforeUser > 0 && (
                <div style={{ fontSize: '0.85rem', color: '#cbd5e1', marginTop: '0.25rem' }}>
                    Estimated wait time: <strong style={{ color: '#a78bfa' }}>{estimatedTimeStr}</strong>
                    <span style={{ fontSize: '0.75rem', color: '#94a3b8', marginLeft: '0.5rem' }}>
                        (Avg service: {status.avgServiceTime} min)
                    </span>
                </div>
            )}
            {status.userTurn && (
                <div style={{ marginTop: '0.5rem', textAlign: 'center' }}>
                    <div style={{ color: '#4ade80', fontWeight: 'bold', fontSize: '0.9rem', animation: 'pulse 2s infinite' }}>
                        📢 IT'S YOUR TURN! PROCEED TO COUNTER
                    </div>
                    {/* <div style={{ color: '#cbd5e1', fontSize: '0.75rem', marginTop: '0.25rem' }}>
                        (SMS notification sent to your registered number)
                    </div> */}
                </div>
            )}
        </div>
    );
};

const MyTokens = () => {
    const [tokens, setTokens] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        if ("Notification" in window && Notification.permission === "default") {
            Notification.requestPermission();
        }

        const fetchTokens = async () => {
            try {
                const response = await getMyTokens();
                setTokens(response.data);
                logInfo('Fetched my tokens');
            } catch (error) {
                console.error('Error fetching tokens:', error);
                logError(`Error fetching my tokens: ${error.message}`);
            } finally {
                setLoading(false);
            }
        };

        fetchTokens();
    }, []);

    if (loading) return <div className="container" style={{ textAlign: 'center' }}>Loading...</div>;

    return (
        <div className="container fade-in">
            <button className="btn" onClick={() => navigate('/dashboard')} style={{ marginBottom: '2rem', padding: '0.5rem 1rem', background: 'transparent', border: '1px solid #8b5cf6' }}>
                &larr; Back to Dashboard
            </button>
            <h2 style={{ marginBottom: '2rem' }}>My Tokens</h2>

            <div className="dashboard-grid">
                {tokens.map((token) => (
                    <div key={token.tokenId} className="glass-card">
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start', marginBottom: '1rem' }}>
                            <div>
                                <h3 style={{ margin: 0, fontSize: '2rem', color: '#8b5cf6' }}>#{token.tokenNumber}</h3>
                                <p style={{ fontSize: '0.9rem', color: '#cbd5e1' }}>{token.serviceName}</p>
                            </div>
                            <span style={{
                                padding: '0.25rem 0.75rem',
                                borderRadius: '999px',
                                fontSize: '0.8rem',
                                backgroundColor:
                                    token.status === 'ISSUED' || token.status === 'WAITING' ? 'rgba(74, 222, 128, 0.2)' :
                                        token.status === 'CALLED' ? 'rgba(139, 92, 246, 0.2)' :
                                            'rgba(148, 163, 184, 0.2)',
                                color:
                                    token.status === 'ISSUED' || token.status === 'WAITING' ? '#4ade80' :
                                        token.status === 'CALLED' ? '#a78bfa' :
                                            '#cbd5e1'
                            }}>
                                {token.status}
                            </span>
                        </div>
                        <p style={{ fontSize: '0.9rem' }}>Office: {token.officeName}</p>
                        <p style={{ fontSize: '0.8rem', color: '#64748b', marginTop: '0.5rem' }}>
                            Generated: {new Date(token.createdAt).toLocaleString()}
                        </p>

                        {(token.status === 'WAITING' || token.status === 'CALLED' || token.status === 'ISSUED') && (
                            <TokenStatusRow token={token} />
                        )}
                    </div>
                ))}
            </div>

            {tokens.length === 0 && (
                <div className="glass-card" style={{ textAlign: 'center', padding: '4rem' }}>
                    <p style={{ fontSize: '1.2rem', color: '#cbd5e1' }}>You haven't generated any tokens yet.</p>
                    <button className="btn" style={{ marginTop: '1rem' }} onClick={() => navigate('/dashboard')}>
                        Find an Office
                    </button>
                </div>
            )}
        </div>
    );
};

export default MyTokens;
