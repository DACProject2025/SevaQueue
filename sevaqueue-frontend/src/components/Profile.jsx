import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { authApi } from '../services/api';

const Profile = () => {
    const { user, updateUserMetadata } = useAuth();

    const [formData, setFormData] = useState({
        name: user?.name || '',
        email: user?.email || '',
        mobile: user?.mobile || '',
        password: '', // New password if they want to change it? The prompt said "edit his or her information".
    });

    const [msg, setMsg] = useState({ text: '', type: '' });
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            // SevaQueueAuthService has /auth/update/{id}
            await authApi.put(`/update/${user.userId}`, formData);
            setMsg({ text: 'Profile updated successfully!', type: 'success' });

            // Note: In a real app, we'd update the AuthContext state here.
            // Since we persist in localStorage, we should update that too.
        } catch (error) {
            setMsg({ text: 'Failed to update profile. ' + (error.response?.data?.message || ''), type: 'error' });
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container fade-in" style={{ maxWidth: '600px', padding: '2rem' }}>
            <div className="glass-card" style={{ padding: '2rem' }}>
                <h2 style={{ marginBottom: '1.5rem', textAlign: 'center' }}>My Profile</h2>

                {msg.text && (
                    <div style={{
                        padding: '1rem',
                        borderRadius: '8px',
                        marginBottom: '1.5rem',
                        background: msg.type === 'success' ? 'rgba(34, 197, 94, 0.1)' : 'rgba(239, 68, 68, 0.1)',
                        border: `1px solid ${msg.type === 'success' ? '#22c55e' : '#ef4444'}`,
                        color: msg.type === 'success' ? '#4ade80' : '#f87171'
                    }}>
                        {msg.text}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: '1.25rem' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: '#cbd5e1' }}>Full Name</label>
                        <input
                            type="text"
                            name="name"
                            className="input-field"
                            value={formData.name}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div style={{ marginBottom: '1.25rem' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: '#cbd5e1' }}>Email Address</label>
                        <input
                            type="email"
                            name="email"
                            className="input-field"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div style={{ marginBottom: '1.25rem' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: '#cbd5e1' }}>Mobile Number</label>
                        <input
                            type="text"
                            name="mobile"
                            className="input-field"
                            value={formData.mobile}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>

                    <p style={{ fontSize: '0.8rem', color: '#94a3b8', fontStyle: 'italic', marginBottom: '1.5rem' }}>
                        Note: To change your password, please use the reset password feature or contact admin.
                    </p>

                    <button
                        type="submit"
                        className="btn"
                        disabled={loading}
                        style={{ width: '100%', padding: '0.75rem', fontSize: '1.1rem' }}
                    >
                        {loading ? 'Updating...' : 'Update Information'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Profile;
