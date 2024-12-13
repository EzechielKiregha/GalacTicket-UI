// store.ts
import { configureStore } from '@reduxjs/toolkit';
import venueReducer from '@/redux/slices/venueSlice';
import eventReducer from '@/redux/slices/eventSlice';
import userReducer from '@/redux/slices/userSlice';
import ticketReducer from '@/redux/slices/ticketSlice';
import cartReducer from '@/redux/slices/cartSlice';
import notificationReducer from '@/redux/slices/notificationSlice';
import themeReducer from '@/redux/slices/themeSlice';

const store = configureStore({
  reducer: {
    venues: venueReducer,
    events: eventReducer,
    users: userReducer,
    tickets: ticketReducer,
    cart: cartReducer,
    notifications: notificationReducer,
    theme: themeReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
