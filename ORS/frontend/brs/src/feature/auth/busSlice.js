// src/features/bus/busSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { getAllBuses } from '../../api/api';

export const fetchAllBuses = createAsyncThunk('bus/fetchAllBuses', async (token, { rejectWithValue }) => {
  try {
    const response = await getAllBuses(token);
    return response.data;
  } catch (error) {
    return rejectWithValue(error.response.data);
  }
});

const busSlice = createSlice({
  name: 'bus',
  initialState: {
    buses: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAllBuses.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchAllBuses.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.buses = action.payload;
      })
      .addCase(fetchAllBuses.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default busSlice.reducer;
