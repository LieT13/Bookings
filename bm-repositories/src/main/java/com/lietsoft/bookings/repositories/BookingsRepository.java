package com.lietsoft.bookings.repositories;

import com.lietsoft.bookings.model.Booking;
import com.lietsoft.bookings.model.exception.NotFoundException;

import java.util.Map;

public interface BookingsRepository {

    Map<String, Booking> findAllBookings();

    Booking findBookingById(String id) throws NotFoundException;

    boolean bookingExists(String id);

    void addBooking(Booking booking);

    void updateBooking(String id, Booking booking) throws NotFoundException;

    void deleteBooking(String id) throws NotFoundException;

}
