import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import AxiosInstance from '@/utils/Axios';

type Event = {
  id: string;
  name: string;
  date: string; // ISO 8601 format
  venue: string; // Venue ID or name based on your requirements
  description: string;
};

type EventState = {
  events: Event[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
};

// Async actions
export const fetchEvents = createAsyncThunk('events/fetchEvents', async () => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/events/all', {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Event[];
});

export const addEvent = createAsyncThunk('events/addEvent', async (event: Event, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.post('/api/events/save', event, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchEvents());
  return response.data as Event;
});

export const updateEvent = createAsyncThunk('events/updateEvent', async (event: Event, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.put(`/api/events/update/${event.id}`, event, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchEvents());
  return response.data as Event;
});

export const deleteEvent = createAsyncThunk('events/deleteEvent', async (id: string, { dispatch }) => {
  const token = localStorage.getItem('token');
  await AxiosInstance.delete(`/api/events/delete/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchEvents());
  return id;
});

// Slice
const eventSlice = createSlice({
  name: 'events',
  initialState: {
    events: [] as Event[],
    status: 'idle',
    error: null,
  } as EventState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchEvents.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchEvents.fulfilled, (state, action: PayloadAction<Event[]>) => {
        state.status = 'succeeded';
        state.events = action.payload;
      })
      .addCase(fetchEvents.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
      .addCase(addEvent.fulfilled, (state, action: PayloadAction<Event>) => {
        state.events.push(action.payload);
      })
      .addCase(updateEvent.fulfilled, (state, action: PayloadAction<Event>) => {
        const index = state.events.findIndex((event) => event.id === action.payload.id);
        if (index !== -1) {
          state.events[index] = action.payload;
        }
      })
      .addCase(deleteEvent.fulfilled, (state, action: PayloadAction<string>) => {
        state.events = state.events.filter((event) => event.id !== action.payload);
      });
  },
});

export default eventSlice.reducer;
