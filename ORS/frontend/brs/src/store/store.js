
import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../feature/auth/authSlice';
import busReducer from '../feature/auth/busSlice';
import reservationReducer from '../feature/auth/reservationSlice';
export const store = configureStore({
  reducer: {
    auth: authReducer,
    bus: busReducer, 
    reservation: reservationReducer,
  },
});


