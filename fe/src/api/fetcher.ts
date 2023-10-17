import axios from 'axios';

const { VITE_API_URL } = import.meta.env;

export const BASE_URL = VITE_API_URL;

export const fetcher = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// fetcher.interceptors.request.use(
//   config => {
//     const authStorage = localStorage.getItem('auth-storage');
//     if (!authStorage) return config;

//     const accessToken = JSON.parse(authStorage).state.accessToken;
//     if (accessToken) {
//       config.headers['Authorization'] = accessToken;
//     }
//     return config;
//   },
//   error => {
//     Promise.reject(error);
//   }
// );
