import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import brandImage from '../assets/sevaqueue-brand.jpg';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await login(email, password);
            navigate('/dashboard');
        } catch (err) {
            setError('Login failed. Please check your credentials.');
        }
    };

    return (
        <div className="container" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', padding: '2rem 1rem' }}>
            <div style={{
                display: 'flex',
                maxWidth: '1100px',
                width: '100%',
                background: 'rgba(255, 255, 255, 0.05)',
                backdropFilter: 'blur(10px)',
                borderRadius: '20px',
                overflow: 'hidden',
                boxShadow: '0 8px 32px 0 rgba(31, 38, 135, 0.37)',
                border: '1px solid rgba(255, 255, 255, 0.18)'
            }}>
                {/* Left Side - Brand Image */}
                <div style={{
                    flex: '1',
                    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    padding: '3rem',
                    minHeight: '600px'
                }}>
                    <img
                        src={brandImage}
                        alt="SevaQueue - Efficient Service, Elevated Experiences"
                        style={{
                            width: '100%',
                            height: 'auto',
                            maxWidth: '450px',
                            objectFit: 'contain',
                            filter: 'drop-shadow(0 10px 20px rgba(0,0,0,0.3))'
                        }}
                    />
                </div>

                {/* Right Side - Login Form */}
                <div style={{
                    flex: '1',
                    padding: '3rem 2.5rem',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    minWidth: '400px'
                }}>
                    <h2 style={{
                        textAlign: 'center',
                        marginBottom: '0.5rem',
                        fontSize: '2rem',
                        fontWeight: '600',
                        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent'
                    }}>
                        Welcome Back
                    </h2>
                    <p style={{
                        textAlign: 'center',
                        marginBottom: '2rem',
                        color: '#cbd5e1',
                        fontSize: '0.95rem'
                    }}>
                        Sign in to continue to SevaQueue
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
                                className="input-field"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="Enter your email"
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
                                    className="input-field"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder="Enter your password"
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
                            Sign In
                        </button>
                    </form>

                    <p style={{
                        textAlign: 'center',
                        marginTop: '1.5rem',
                        fontSize: '0.9rem',
                        color: '#cbd5e1'
                    }}>
                        Don't have an account?{' '}
                        <a
                            href="/register"
                            style={{
                                color: '#8b5cf6',
                                fontWeight: '600',
                                textDecoration: 'none'
                            }}
                        >
                            Create Account
                        </a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login;
