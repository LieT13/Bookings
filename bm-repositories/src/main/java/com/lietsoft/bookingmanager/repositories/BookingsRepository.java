package com.lietsoft.bookingmanager.repositories;

import com.lietsoft.bookingmanager.model.Booking;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;

import java.util.Map;

public interface BookingsRepository {

    Map<String, Booking> findAllBookings();

    Booking findBookingById(String id) throws NotFoundException;

    boolean bookingExists(String id);

    void addBooking(Booking booking);

    void updateBooking(String id, Booking booking) throws NotFoundException;

    void deleteBooking(String id) throws NotFoundException;

}
