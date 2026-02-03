import { createApi } from './api';
import axios from 'axios';

// Since we cannot easily export/import `createApi` helper if it wasn't exported,
// I'll assume we can reuse `mainApi` if it points to `http://localhost:8081/api`
// Token Controller is at `/api/tokens`.
// Office Service Controller is at `/api/services`.
// So `mainApi` is perfect.

// Helper functions for services
import { mainApi } from './api';

export const fetchServicesByOffice = (officeId) => {
    return mainApi.get(`/services/office/${officeId}`);
};

export const generateToken = (serviceId) => {
    // Post request with query param
    return mainApi.post(`/tokens/generate-token?serviceId=${serviceId}`);
};

export const getMyTokens = () => {
    return mainApi.get('/tokens/my-tokens');
};
export const getQueueStatus = (tokenId) => {
    return mainApi.get(`/tokens/${tokenId}/queue-status`);
};
