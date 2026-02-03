import { loggerApi } from './api';

const log = async (level, message, serviceName = 'Frontend') => {
  try {
    await loggerApi.post('', {
      Level: level,
      Message: message,
      ServiceName: serviceName,
    });
  } catch (error) {
    console.error('Failed to send log:', error);
  }
};

export const logInfo = (message) => log('Information', message);
export const logWarning = (message) => log('Warning', message);
export const logError = (message) => log('Error', message);
