import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/reservations';

// Fetch user reservations
export const fetchUserReservations = createAsyncThunk(
  'reservation/fetchUserReservations',
  async (token, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${API_URL}/user`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

// Create a reservation
export const createReservation = createAsyncThunk(
  'reservation/create',
  async ({ reservationData, token }, { rejectWithValue }) => {
    try {
      const response = await axios.put(API_URL, reservationData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

// Cancel a reservation
export const cancelReservation = createAsyncThunk(
  'reservation/cancelReservation',
  async ({ reservationId, token }, { rejectWithValue }) => {
    try {
      await axios.delete(`${API_URL}/${reservationId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return reservationId; // Return the id to remove from state
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const reservationSlice = createSlice({
  name: 'reservation',
  initialState: {
    reservations: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUserReservations.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchUserReservations.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.reservations = action.payload;
      })
      .addCase(fetchUserReservations.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      .addCase(createReservation.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(createReservation.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.reservations.push(action.payload);
      })
      .addCase(createReservation.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      .addCase(cancelReservation.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(cancelReservation.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.reservations = state.reservations.filter(
          (reservation) => reservation.id !== action.payload
        );
      })
      .addCase(cancelReservation.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default reservationSlice.reducer;
