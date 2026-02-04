import { mainApi } from './api';
import axios from 'axios';

export const createOffice = (officeData) => {
    return mainApi.post('/offices', officeData);
};

export const deactivateOffice = (officeId) => {
    return mainApi.put(`/offices/${officeId}/deactivate`);
};

export const fetchAllOffices = () => {
    return mainApi.get('/offices/all');
};

export const toggleOfficeStatus = (officeId) => {
    return mainApi.put(`/offices/${officeId}/toggle-status`);
};

export const createService = (officeId, serviceData) => {
    return mainApi.post(`/services?officeId=${officeId}`, serviceData);
};

export const deactivateService = (serviceId) => {
    return mainApi.put(`/services/${serviceId}/deactivate`);
};

export const fetchAllServicesByOffice = (officeId) => {
    return mainApi.get(`/services/office/${officeId}/all`);
};

export const toggleServiceStatus = (serviceId) => {
    return mainApi.put(`/services/${serviceId}/toggle-status`);
};

export const createCounter = (counterData) => {
    return mainApi.post('/counter/create', counterData);
};

export const assignCounter = (serviceId, staffId, counterNumber) => {
    return mainApi.post(`/counter/assign?serviceId=${serviceId}&staffId=${staffId}&counterNumber=${counterNumber}`);
};

export const registerStaff = (staffData) => {
    // Auth service is on port 8080. mainApi is on 8081.
    // We need to use authApi or full URL.
    // Let's import authApi from ./api or create a helper if not available.
    // We previously saw authApi in api.js? Let's check api.js content again or blindly import it.
    // If api.js exports authApi, we can use it.
    // But adminService.js currently imports mainApi.
    // Let's assume we can change the import.
    // Wait, let's verify api.js exports first.
    return axios.post('http://localhost:8080/auth/register-staff', staffData, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    });
};

export const getAllStaff = () => {
    return axios.get('http://localhost:8080/auth/staff', {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    });
};
