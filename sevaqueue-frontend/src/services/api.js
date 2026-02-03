import axios from 'axios';

export const createApi = (baseURL) => {
    const api = axios.create({
        baseURL,
        headers: {
            'Content-Type': 'application/json',
        },
    });

    api.interceptors.request.use(
        (config) => {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );

    return api;
};

// Auth Service (Spring Boot) - Port 8080 (Default)
export const authApi = createApi('http://localhost:8080/auth');

// Main Service (Spring Boot) - Port 8081
export const mainApi = createApi('http://localhost:8081/api');

// Logger Service (.NET) - Port 5090
// Note: The Logger Service might not require JWT, but we'll include it if needed or the interceptor is harmless.
// If it fails with CORS due to header, we might need a separate instance without the interceptor.
export const loggerApi = axios.create({
    baseURL: 'http://localhost:5090/api/logs',
    headers: {
        'Content-Type': 'application/json',
    },
});
