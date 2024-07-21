// src/features/auth/authSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { register, login } from '../../api/api';

// Define async thunks for registration and login
export const registerUserThunk = createAsyncThunk('auth/register', async (userData, { rejectWithValue }) => {
  try {
    const response = await register(userData);
    return response.data;
  } catch (error) {
    return rejectWithValue(error.response.data);
  }
});

export const loginUserThunk = createAsyncThunk('auth/login', async (userData, { rejectWithValue }) => {
  try {
    const response = await login(userData);
    return response.data;
  } catch (error) {
    return rejectWithValue(error.response.data);
  }
});

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: null,
    token: null,
    status: 'idle',
    error: null,
  },
  reducers: {
    logout: (state) => {
      // Clear user data and token on logout
      state.user = null;
      state.token = null;
      localStorage.removeItem('token');
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerUserThunk.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(registerUserThunk.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.user = action.payload.username;
        state.token = action.payload.token;
        state.error = null;
        localStorage.setItem('token', action.payload.token); // Save token
      })
      .addCase(registerUserThunk.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      .addCase(loginUserThunk.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(loginUserThunk.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.user = action.payload.username;
        state.token = action.payload.token;
        state.error = null;
        localStorage.setItem('token', action.payload.token); // Save token
      })
      .addCase(loginUserThunk.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export const { logout } = authSlice.actions;

export default authSlice.reducer;
