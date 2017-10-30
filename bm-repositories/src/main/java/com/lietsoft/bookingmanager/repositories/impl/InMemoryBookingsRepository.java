package com.lietsoft.bookingmanager.repositories.impl;

import com.lietsoft.bookingmanager.model.Booking;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.repositories.BookingsRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryBookingsRepository implements BookingsRepository {

    private static final Map<String, Booking> bookings = Collections.synchronizedMap(new HashMap<String, Booking>());

    @Override
    public Map<String, Booking> findAllBookings() {
        return bookings;
    }

    @Override
    public Booking findBookingById(String id) throws NotFoundException {
        synchronized (bookings) {
            if (bookings.get(id) == null) {
                throw new NotFoundException("Booking does not exist!");
            }
            return bookings.get(id);
        }
    }

    @Override
    public boolean bookingExists(String id) {
        return bookings.containsKey(id);
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.put(UUID.randomUUID().toString(), booking);
    }

    @Override
    public void updateBooking(String id, Booking booking) throws NotFoundException {
        synchronized (bookings) {
            if (bookings.get(id) == null) {
                throw new NotFoundException("Booking does not exist!");
            }
            bookings.put(id, booking);
        }
    }

    @Override
    public void deleteBooking(String id) throws NotFoundException {
        synchronized (bookings) {
            if (bookings.get(id) == null) {
                throw new NotFoundException("Booking does not exist!");
            }
            bookings.remove(id);
        }
    }

    public static void setBookings(Map<String, Booking> newBookings) {
        bookings.clear();
        bookings.putAll(newBookings);
    }

}
