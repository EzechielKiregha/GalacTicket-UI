import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import AxiosInstance from '@/utils/Axios';

type Venue = {
  id: string;
  name: string;
  location: string;
  capacity: number;
  description: string;
};

type VenueState = {
  venues: Venue[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
};

// Async actions
export const fetchVenues = createAsyncThunk('venues/fetchVenues', async () => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/venues/all', {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Venue[];
});

export const addVenue = createAsyncThunk('venues/addVenue', async (venue: Venue, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.post('/api/venues/save', venue, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchVenues());
  return response.data as Venue;
});

export const updateVenue = createAsyncThunk('venues/updateVenue', async (venue: Venue, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.put(`/api/venues/${venue.id}`, venue, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchVenues());
  return response.data as Venue;
});

export const deleteVenue = createAsyncThunk('venues/deleteVenue', async (id: string, { dispatch }) => {
  const token = localStorage.getItem('token');
  await AxiosInstance.delete(`/api/venues/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchVenues());
  return id;
});

export const fetchVenuesByLocation = createAsyncThunk('venues/fetchByLocation', async (location: string) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get(`/api/venues/location/${location}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Venue[];
});

export const fetchVenuesByMinCapacity = createAsyncThunk('venues/fetchByMinCapacity', async (capacity: number) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get(`/api/venues/capacity/min/${capacity}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Venue[];
});

export const fetchVenuesByMaxCapacity = createAsyncThunk('venues/fetchByMaxCapacity', async (capacity: number) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get(`/api/venues/capacity/max/${capacity}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Venue[];
});

// Slice
const venueSlice = createSlice({
  name: 'venues',
  initialState: {
    venues: [] as Venue[],
    status: 'idle',
    error: null,
  } as VenueState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchVenues.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchVenues.fulfilled, (state, action: PayloadAction<Venue[]>) => {
        state.status = 'succeeded';
        state.venues = action.payload;
      })
      .addCase(fetchVenues.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
      .addCase(addVenue.fulfilled, (state, action: PayloadAction<Venue>) => {
        state.venues.push(action.payload);
      })
      .addCase(updateVenue.fulfilled, (state, action: PayloadAction<Venue>) => {
        const index = state.venues.findIndex((venue) => venue.id === action.payload.id);
        if (index !== -1) {
          state.venues[index] = action.payload;
        }
      })
      .addCase(deleteVenue.fulfilled, (state, action: PayloadAction<string>) => {
        state.venues = state.venues.filter((venue) => venue.id !== action.payload);
      })
      .addCase(fetchVenuesByLocation.fulfilled, (state, action: PayloadAction<Venue[]>) => {
        state.venues = action.payload;
      })
      .addCase(fetchVenuesByMinCapacity.fulfilled, (state, action: PayloadAction<Venue[]>) => {
        state.venues = action.payload;
      })
      .addCase(fetchVenuesByMaxCapacity.fulfilled, (state, action: PayloadAction<Venue[]>) => {
        state.venues = action.payload;
      });
  },
});

export default venueSlice.reducer;
