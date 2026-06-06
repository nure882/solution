import api from './axios';

// Auth
export const login = (email, password) => api.post('/api/auth/login', { email, password });
export const register = (email, password, fullName) => api.post('/api/auth/register', { email, password, fullName });
export const getMe = () => api.get('/api/auth/me');

// Stations
export const getStations = (city = '', address = '') =>
  api.get('/api/stations', { params: { city: city || undefined, address: address || undefined } });
export const getStation = (id) => api.get(`/api/stations/${id}`);
export const createStation = (body) => api.post('/api/stations', body);
export const updateStation = (id, body) => api.put(`/api/stations/${id}`, body);
export const deleteStation = (id) => api.delete(`/api/stations/${id}`);

// Gas types
export const getGasTypes = (name = '') =>
  api.get('/api/gas-types', { params: { name: name || undefined } });
export const createGasType = (name) => api.post('/api/gas-types', { name });
export const updateGasType = (id, name) => api.put(`/api/gas-types/${id}`, { name });
export const deleteGasType = (id) => api.delete(`/api/gas-types/${id}`);

// Pumps
export const getPumps = (stationId) =>
  api.get('/api/pumps', { params: { stationId: stationId || undefined } });
export const getPump = (id) => api.get(`/api/pumps/${id}`);
export const createPump = (body) => api.post('/api/pumps', body);
export const updatePump = (id, body) => api.put(`/api/pumps/${id}`, body);
export const deletePump = (id) => api.delete(`/api/pumps/${id}`);

// Coupons
export const getCoupons = () => api.get('/api/coupons');
export const getMyCoupons = () => api.get('/api/coupons/my');
export const createCoupon = (body) => api.post('/api/coupons', body);
export const updateCoupon = (id, body) => api.put(`/api/coupons/${id}`, body);
export const deleteCoupon = (id) => api.delete(`/api/coupons/${id}`);

// Users
export const getUsers = (role) =>
  api.get('/api/users', { params: { role: role || undefined } });
export const getUser = (id) => api.get(`/api/users/${id}`);
export const createUser = (body) => api.post('/api/users', body);
export const setUserBan = (id, banned) => api.put(`/api/users/${id}/ban`, null, { params: { banned } });
export const deleteUser = (id) => api.delete(`/api/users/${id}`);
