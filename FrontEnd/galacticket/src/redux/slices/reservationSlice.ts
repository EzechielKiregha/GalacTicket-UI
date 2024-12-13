import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import AxiosInstance from '@/utils/Axios';

type Reservation = {
  id: string;
  event: string; // Event ID or name
  reservationDate: string; // ISO string format for dates
  quantity: number;
  status: string;
  customer: string; // Customer ID or name
};

type ReservationState = {
  reservations: Reservation[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
};

// Async actions
export const fetchReservations = createAsyncThunk('reservations/fetchReservations', async () => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/reservations/all', {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Reservation[];
});

export const addReservation = createAsyncThunk('reservations/addReservation', async (reservation: Reservation, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.post('/api/reservations/save', reservation, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchReservations());
  return response.data as Reservation;
});

export const updateReservation = createAsyncThunk('reservations/updateReservation', async ({ id, updatedReservation }: { id: string; updatedReservation: Reservation }, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.put(`/api/reservations/update/${id}`, updatedReservation, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchReservations());
  return response.data as Reservation;
});

export const deleteReservation = createAsyncThunk('reservations/deleteReservation', async (id: string, { dispatch }) => {
  const token = localStorage.getItem('token');
  await AxiosInstance.delete(`/api/reservations/delete/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchReservations());
  return id;
});

// Slice
const reservationSlice = createSlice({
  name: 'reservations',
  initialState: {
    reservations: [] as Reservation[],
    status: 'idle',
    error: null,
  } as ReservationState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchReservations.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchReservations.fulfilled, (state, action: PayloadAction<Reservation[]>) => {
        state.status = 'succeeded';
        state.reservations = action.payload;
      })
      .addCase(fetchReservations.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
      .addCase(addReservation.fulfilled, (state, action: PayloadAction<Reservation>) => {
        state.reservations.push(action.payload);
      })
      .addCase(updateReservation.fulfilled, (state, action: PayloadAction<Reservation>) => {
        const index = state.reservations.findIndex((reservation) => reservation.id === action.payload.id);
        if (index !== -1) {
          state.reservations[index] = action.payload;
        }
      })
      .addCase(deleteReservation.fulfilled, (state, action: PayloadAction<string>) => {
        state.reservations = state.reservations.filter((reservation) => reservation.id !== action.payload);
      });
  },
});

export default reservationSlice.reducer;
