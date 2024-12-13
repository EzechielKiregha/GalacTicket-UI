import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import AxiosInstance from '@/utils/Axios';

type User = {
  id: string;
  name: string;
  email: string;
  role: string; // Example: 'admin', 'user'
};

type UserState = {
  user: User | null;
  token: string | null;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
};

// Initial state
const initialState: UserState = {
  user: null,
  token: null,
  status: 'idle',
  error: null,
};

// Async actions

// Login action
export const loginUser = createAsyncThunk(
  'user/loginUser',
  async (credentials: { email: string; password: string }) => {
    const response = await AxiosInstance.post('/api/auth/login', credentials);
    localStorage.setItem('token', response.data.token);
    return response.data; // Assume response contains { user: User, token: string }
  }
);

// Fetch user profile
export const fetchUserProfile = createAsyncThunk(
  'user/fetchUserProfile',
  async () => {
    const token = localStorage.getItem('token');
    const response = await AxiosInstance.get('/api/auth/profile', {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data as User;
  }
);

// Logout action
export const logoutUser = createAsyncThunk('user/logoutUser', async () => {
  localStorage.removeItem('token');
  return null;
});

// Slice
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Login cases
      .addCase(loginUser.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action: PayloadAction<{ user: User; token: string }>) => {
        state.status = 'succeeded';
        state.user = action.payload.user;
        state.token = action.payload.token;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })

      // Fetch user profile cases
      .addCase(fetchUserProfile.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchUserProfile.fulfilled, (state, action: PayloadAction<User>) => {
        state.status = 'succeeded';
        state.user = action.payload;
      })
      .addCase(fetchUserProfile.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })

      // Logout cases
      .addCase(logoutUser.fulfilled, (state) => {
        state.user = null;
        state.token = null;
        state.status = 'idle';
      });
  },
});

export default userSlice.reducer;
