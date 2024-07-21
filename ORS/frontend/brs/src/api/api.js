// src/api/api.js
import axios from 'axios';

const API_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add token to headers if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// Response interceptor to handle errors globally
api.interceptors.response.use((response) => {
  return response;
}, (error) => {
  if (error.response && error.response.status === 401) {
    // Handle unauthorized errors, e.g., redirect to login
  }
  return Promise.reject(error);
});

export const register = async (userData) => {
  try {
    const response = await api.post('/auth/register', userData);
    return response;
  } catch (error) {
    throw error;
  }
};

export const login = async (userData) => {
  try {
    const response = await api.post('/auth/login', userData);
    return response;
  } catch (error) {
    throw error;
  }
};

export const getAllBuses = async () => {
  try {
    const response = await api.get('/api/bus/all');
    return response;
  } catch (error) {
    throw error;
  }
};

