import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import AxiosInstance from '@/utils/Axios';

type Ticket = {
  ticketId: string;
  relatedEvent: { eventId: string; eventName: string };
  purchaser: { customerId: string; customerName: string };
  category: string;
  cost: number;
  currentStatus: string;
};

type TicketState = {
  tickets: Ticket[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
};

// Async actions
export const fetchTicketById = createAsyncThunk('tickets/fetchTicketById', async (id: string) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get(`/api/tickets/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Ticket;
});

export const fetchTicketsByEvent = createAsyncThunk('tickets/fetchTicketsByEvent', async (eventId: string) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/tickets/by-event', {
    params: { eventId },
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Ticket[];
});

export const fetchTicketsByCustomer = createAsyncThunk('tickets/fetchTicketsByCustomer', async (customerId: string) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/tickets/by-customer', {
    params: { customerId },
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Ticket[];
});

export const fetchTicketsByStatus = createAsyncThunk('tickets/fetchTicketsByStatus', async (status: string) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.get('/api/tickets/by-status', {
    params: { status },
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data as Ticket[];
});

export const addTicket = createAsyncThunk('tickets/addTicket', async (ticket: Ticket, { dispatch }) => {
  const token = localStorage.getItem('token');
  const response = await AxiosInstance.post('/api/tickets/save', ticket, {
    headers: { Authorization: `Bearer ${token}` },
  });
  dispatch(fetchTicketsByEvent(ticket.relatedEvent.eventId));
  return response.data as Ticket;
});

export const deleteTicket = createAsyncThunk('tickets/deleteTicket', async (id: string, { dispatch }) => {
  const token = localStorage.getItem('token');
  await AxiosInstance.delete(`/api/tickets/delete/${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return id;
});

// Slice
const ticketSlice = createSlice({
  name: 'tickets',
  initialState: {
    tickets: [] as Ticket[],
    status: 'idle',
    error: null,
  } as TicketState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchTicketById.fulfilled, (state, action: PayloadAction<Ticket>) => {
        state.tickets = [action.payload];
        state.status = 'succeeded';
      })
      .addCase(fetchTicketsByEvent.fulfilled, (state, action: PayloadAction<Ticket[]>) => {
        state.tickets = action.payload;
        state.status = 'succeeded';
      })
      .addCase(fetchTicketsByCustomer.fulfilled, (state, action: PayloadAction<Ticket[]>) => {
        state.tickets = action.payload;
        state.status = 'succeeded';
      })
      .addCase(fetchTicketsByStatus.fulfilled, (state, action: PayloadAction<Ticket[]>) => {
        state.tickets = action.payload;
        state.status = 'succeeded';
      })
      .addCase(addTicket.fulfilled, (state, action: PayloadAction<Ticket>) => {
        state.tickets.push(action.payload);
      })
      .addCase(deleteTicket.fulfilled, (state, action: PayloadAction<string>) => {
        state.tickets = state.tickets.filter((ticket) => ticket.ticketId !== action.payload);
      })
      .addMatcher(
        (action) => action.type.endsWith('/pending'),
        (state) => {
          state.status = 'loading';
        }
      )
      .addMatcher(
        (action) => action.type.endsWith('/rejected'),
        (state, action) => {
          state.status = 'failed';
          state.error = action.type || null;
        }
      );
  },
});

export default ticketSlice.reducer;
