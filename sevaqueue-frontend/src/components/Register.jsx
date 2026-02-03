import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        mobileNumber: '',
        role: 'USER'
    });
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState('');
    const { register } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                name: `${formData.firstName} ${formData.lastName}`.trim(),
                email: formData.email,
                password: formData.password,
                mobile: formData.mobileNumber
            };
            await register(payload);
            navigate('/login');
        } catch (err) {
            setError('Registration failed. Please try again.');
        }
    };

    return (
        <div className="container" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', padding: '2rem 1rem' }}>
            <div className="glass-card fade-in" style={{ width: '100%', maxWidth: '550px', padding: '2.5rem' }}>
                <h2 style={{
                    textAlign: 'center',
                    marginBottom: '0.5rem',
                    fontSize: '2rem',
                    fontWeight: '600',
                    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                    WebkitBackgroundClip: 'text',
                    WebkitTextFillColor: 'transparent'
                }}>
                    Create Account
                </h2>
                <p style={{
                    textAlign: 'center',
                    marginBottom: '2rem',
                    color: '#cbd5e1',
                    fontSize: '0.95rem'
                }}>
                    Join SevaQueue to manage your time efficiently
                </p>

                {error && (
                    <div style={{
                        color: '#ff6b6b',
                        marginBottom: '1.5rem',
                        textAlign: 'center',
                        padding: '0.75rem',
                        background: 'rgba(255, 107, 107, 0.1)',
                        borderRadius: '8px',
                        border: '1px solid rgba(255, 107, 107, 0.3)'
                    }}>
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', marginBottom: '1.25rem' }}>
                        <div>
                            <label style={{
                                display: 'block',
                                marginBottom: '0.5rem',
                                fontSize: '0.9rem',
                                fontWeight: '500',
                                color: '#e2e8f0'
                            }}>
                                First Name
                            </label>
                            <input
                                type="text"
                                name="firstName"
                                className="input-field"
                                onChange={handleChange}
                                placeholder="Enter first name"
                                required
                                style={{
                                    width: '100%',
                                    padding: '0.75rem 1rem',
                                    fontSize: '0.95rem'
                                }}
                            />
                        </div>
                        <div>
                            <label style={{
                                display: 'block',
                                marginBottom: '0.5rem',
                                fontSize: '0.9rem',
                                fontWeight: '500',
                                color: '#e2e8f0'
                            }}>
                                Last Name
                            </label>
                            <input
                                type="text"
                                name="lastName"
                                className="input-field"
                                onChange={handleChange}
                                placeholder="Enter last name"
                                required
                                style={{
                                    width: '100%',
                                    padding: '0.75rem 1rem',
                                    fontSize: '0.95rem'
                                }}
                            />
                        </div>
                    </div>

                    <div style={{ marginBottom: '1.25rem' }}>
                        <label style={{
                            display: 'block',
                            marginBottom: '0.5rem',
                            fontSize: '0.9rem',
                            fontWeight: '500',
                            color: '#e2e8f0'
                        }}>
                            Email Address
                        </label>
                        <input
                            type="email"
                            name="email"
                            className="input-field"
                            onChange={handleChange}
                            placeholder="Enter your email"
                            required
                            style={{
                                width: '100%',
                                padding: '0.75rem 1rem',
                                fontSize: '0.95rem'
                            }}
                        />
                    </div>

                    <div style={{ marginBottom: '1.25rem' }}>
                        <label style={{
                            display: 'block',
                            marginBottom: '0.5rem',
                            fontSize: '0.9rem',
                            fontWeight: '500',
                            color: '#e2e8f0'
                        }}>
                            Mobile Number
                        </label>
                        <input
                            type="text"
                            name="mobileNumber"
                            className="input-field"
                            onChange={handleChange}
                            placeholder="Enter your mobile number"
                            required
                            style={{
                                width: '100%',
                                padding: '0.75rem 1rem',
                                fontSize: '0.95rem'
                            }}
                        />
                    </div>

                    <div style={{ marginBottom: '1.5rem' }}>
                        <label style={{
                            display: 'block',
                            marginBottom: '0.5rem',
                            fontSize: '0.9rem',
                            fontWeight: '500',
                            color: '#e2e8f0'
                        }}>
                            Password
                        </label>
                        <div style={{ position: 'relative' }}>
                            <input
                                type={showPassword ? "text" : "password"}
                                name="password"
                                className="input-field"
                                onChange={handleChange}
                                placeholder="Create a password"
                                required
                                style={{
                                    width: '100%',
                                    padding: '0.75rem 1rem',
                                    paddingRight: '3rem',
                                    fontSize: '0.95rem'
                                }}
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
                                    fontSize: '1.2rem',
                                    padding: '0.25rem'
                                }}
                            >
                                {showPassword ? '👁️' : '👁️‍🗨️'}
                            </button>
                        </div>
                    </div>

                    <button
                        type="submit"
                        className="btn"
                        style={{
                            width: '100%',
                            padding: '0.875rem',
                            fontSize: '1rem',
                            fontWeight: '600',
                            background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                            border: 'none',
                            transition: 'transform 0.2s, box-shadow 0.2s'
                        }}
                        onMouseEnter={(e) => {
                            e.target.style.transform = 'translateY(-2px)';
                            e.target.style.boxShadow = '0 10px 25px rgba(102, 126, 234, 0.4)';
                        }}
                        onMouseLeave={(e) => {
                            e.target.style.transform = 'translateY(0)';
                            e.target.style.boxShadow = 'none';
                        }}
                    >
                        Create Account
                    </button>
                </form>

                <p style={{
                    textAlign: 'center',
                    marginTop: '1.5rem',
                    fontSize: '0.9rem',
                    color: '#cbd5e1'
                }}>
                    Already have an account?{' '}
                    <a
                        href="/login"
                        style={{
                            color: '#8b5cf6',
                            fontWeight: '600',
                            textDecoration: 'none'
                        }}
                    >
                        Sign In
                    </a>
                </p>
            </div>
        </div>
    );
};

export default Register;
