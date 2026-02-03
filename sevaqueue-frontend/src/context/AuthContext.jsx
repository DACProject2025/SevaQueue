import React, { createContext, useState, useContext, useEffect } from 'react';
import { authApi } from '../services/api';
import { logInfo, logError } from '../services/loggerService';
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const decodeAndSetUser = (token, details = null) => {
        try {
            const decoded = jwtDecode(token);
            console.log("DEBUG: Decoded JWT:", decoded);

            const userData = {
                email: details?.email || decoded.sub,
                role: details?.role || decoded.role,
                userId: details?.userId || decoded.userId
            };

            setUser({
                token,
                ...userData
            });

            if (userData.userId) {
                localStorage.setItem('user', JSON.stringify(userData));
            }
        } catch (error) {
            console.error("Invalid token", error);
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            setUser(null);
        }
    };

    useEffect(() => {
        const token = localStorage.getItem('token');
        const storedUser = localStorage.getItem('user');

        if (token) {
            if (storedUser) {
                try {
                    const userData = JSON.parse(storedUser);
                    setUser({ ...userData, token });
                    console.log("DEBUG: Restored user from localStorage:", userData);
                } catch (e) {
                    decodeAndSetUser(token);
                }
            } else {
                decodeAndSetUser(token);
            }
        }
        setLoading(false);
    }, []);

    const login = async (email, password) => {
        try {
            const response = await authApi.post('/login', { email, password });
            const data = response.data;
            const token = data.accessToken || data.token;

            localStorage.setItem('token', token);
            // Pass the details from response to decodeAndSetUser
            decodeAndSetUser(token, {
                userId: data.userId,
                role: data.role,
                email: data.email
            });
            logInfo(`User logged in: ${email}`);
            return true;
        } catch (error) {
            logError(`Login failed for ${email}: ${error.message}`);
            throw error;
        }
    };

    const register = async (userData) => {
        try {
            await authApi.post('/register', userData);
            logInfo(`User registered: ${userData.email}`);
            return true;
        } catch (error) {
            logError(`Registration failed for ${userData.email}: ${error.message}`);
            throw error;
        }
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setUser(null);
        logInfo('User logged out');
    };

    const updateUserMetadata = (newMetadata) => {
        if (!user) return;

        const updatedUser = { ...user, ...newMetadata };
        setUser(updatedUser);

        // Persist to localStorage (excluding token)
        const { token, ...userData } = updatedUser;
        localStorage.setItem('user', JSON.stringify(userData));
    };

    return (
        <AuthContext.Provider value={{ user, login, register, logout, updateUserMetadata, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
