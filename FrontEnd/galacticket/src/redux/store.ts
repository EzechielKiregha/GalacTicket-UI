// store.ts
import { configureStore } from '@reduxjs/toolkit';
import venueReducer from '@/redux/slices/venueSlice';
import eventReducer from '@/redux/slices/eventSlice';
import userReducer from '@/redux/slices/userSlice';
import ticketReducer from '@/redux/slices/ticketSlice';
// import cartReducer from '@/redux/slices/cartSlice';
import reservationReducer from '@/redux/slices/reservationSlice';
// import themeReducer from '@/redux/slices/customerSlice';

const store = configureStore({
  reducer: {
    venues: venueReducer,
    events: eventReducer,
    users: userReducer,
    tickets: ticketReducer,
    // cart: cartReducer,
    reservations: reservationReducer,
    // theme: themeReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
