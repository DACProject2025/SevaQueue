import { mainApi } from './api';

export const callNextToken = (serviceId, counterId) => {
    return mainApi.post(`/tokens/call-next?serviceId=${serviceId}&counterId=${counterId}`);
};

export const updateTokenStatus = (tokenId, status) => {
    // status enum: ISSUED, CALLED, WIP, COMPLETED, SKIPPED, CANCELLED
    return mainApi.put(`/tokens/${tokenId}/status?status=${status}`);
};

export const updateCounterStatus = (counterId, status) => {
    // status enum: ACTIVE, INACTIVE, BREAK, LUNCH
    return mainApi.put(`/counter/${counterId}/status?status=${status}`);
};

export const getTokensByService = (serviceId) => {
    return mainApi.get(`/tokens/service/${serviceId}`);
};
