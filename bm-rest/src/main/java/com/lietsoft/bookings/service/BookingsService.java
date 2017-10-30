package com.lietsoft.bookings.service;

import com.lietsoft.bookings.repositories.impl.InMemoryBookingsRepository;
import com.lietsoft.bookings.model.Booking;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.BookingsRepository;

import java.util.Map;

public class BookingsService {

    private BookingsRepository bookingsRepository;

    public BookingsService() {
        bookingsRepository = new InMemoryBookingsRepository();
    }

    public Map<String, Booking> getAllBookings() {
        return bookingsRepository.findAllBookings();
    }

    public Booking getBooking(String bookingId) throws NotFoundException {
        if (!bookingsRepository.bookingExists(bookingId)) {
            throw new NotFoundException("Booking does not exist");
        }
        return bookingsRepository.findBookingById(bookingId);
    }

    public void addBooking(Booking booking) {
        bookingsRepository.addBooking(booking);
    }

    public void updateBooking(String bookingId, Booking booking) throws NotFoundException {
        if (!bookingsRepository.bookingExists(bookingId)) {
            throw new NotFoundException("Booking does not exist");
        }
        Booking oldBooking = bookingsRepository.findBookingById(bookingId);
        updateBookingData(oldBooking, booking);
    }

    public void deleteBooking(String bookingId) throws NotFoundException {
        if (!bookingsRepository.bookingExists(bookingId)) {
            throw new NotFoundException("Booking does not exist");
        }
        bookingsRepository.deleteBooking(bookingId);
    }

    private void updateBookingData(Booking oldBooking, Booking booking) {
        if (booking.getUser() != null) {
            oldBooking.setUser(booking.getUser());
        }
        if (booking.getFlight() != null) {
            oldBooking.setFlight(booking.getFlight());
        }
    }

    public void setBookingsRepository(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

}
