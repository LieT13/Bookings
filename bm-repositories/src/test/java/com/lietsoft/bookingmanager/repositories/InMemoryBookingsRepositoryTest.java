package com.lietsoft.bookingmanager.repositories;

import com.lietsoft.bookingmanager.model.Booking;
import com.lietsoft.bookingmanager.repositories.impl.InMemoryBookingsRepository;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryBookingsRepositoryTest {

    private static final long NUM_BOOKINGS = 2;

    private static final String NEW_BOOKING = "newBooking";

    private InMemoryBookingsRepository bookingsRepository;

    @Before
    public void setUp() {
        bookingsRepository = new InMemoryBookingsRepository();
    }

    @Test
    public void testFindAllBookings() {
        InMemoryBookingsRepository.setBookings(initBookings());
        Map<String, Booking> allBookings = bookingsRepository.findAllBookings();
        assertTrue(allBookings.size() == NUM_BOOKINGS);
    }

    @Test
    public void testFindBookingById() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            Booking booking = bookingsRepository.findBookingById("0");
            assertEquals("user0", booking.getUser());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent booking!");
        }
    }

    @Test
    public void testFindNotFoundBookingById() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            bookingsRepository.findBookingById("5");
            fail("NotFoundException not thrown for not found booking!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testAddBooking() {
        InMemoryBookingsRepository.setBookings(initBookings());
        Booking b = new Booking("user5", "address0");
        bookingsRepository.addBooking(b);
        assertTrue(bookingsRepository.findAllBookings().values().contains(b));
    }

    @Test
    public void testUpdateBooking() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            bookingsRepository.updateBooking("0", createBooking(NEW_BOOKING, "flight1"));
            Assert.assertEquals("newBooking", bookingsRepository.findAllBookings().get("0").getUser());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent booking!");
        }
    }

    @Test
    public void testUpdateNotFoundBooking() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            bookingsRepository.updateBooking("5", createBooking(NEW_BOOKING, "flight1"));
            fail("NotFoundException not thrown for not found booking!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteBooking() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            bookingsRepository.deleteBooking("0");
            assertNull(bookingsRepository.findAllBookings().get("0"));
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent booking!");
        }
    }

    @Test
    public void testDeleteNotFoundBooking() {
        InMemoryBookingsRepository.setBookings(initBookings());
        try {
            bookingsRepository.deleteBooking("5");
            fail("NotFoundException not thrown for not found booking!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    private HashMap<String, Booking> initBookings() {
        HashMap<String, Booking> bookings = new HashMap<>();
        for (long i = 0; i< NUM_BOOKINGS; i++) {
            bookings.put(String.valueOf(i), createBooking("user" + i, "flight" + i));
        }
        return bookings;
    }

    private Booking createBooking(String userId, String flightId) {
        return new Booking(userId, flightId);
    }

}
