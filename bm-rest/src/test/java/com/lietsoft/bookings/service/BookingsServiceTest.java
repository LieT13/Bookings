package com.lietsoft.bookings.service;

import com.lietsoft.bookings.model.Booking;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.BookingsRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingsServiceTest {

    private static final int NUM_BOOKINGS = 2;

    private static final String FLIGHT_ID = "flight1";
    private static final String USER_ID = "user1";

    private BookingsService bookingsService;

    private BookingsRepository mockBookingsRepository;

    @Before
    public void setUp() {
        bookingsService = new BookingsService();
        mockBookingsRepository = mock(BookingsRepository.class);
        bookingsService.setBookingsRepository(mockBookingsRepository);
    }

    @Test
    public void testGetAllBookings() {
        when(mockBookingsRepository.findAllBookings()).thenReturn(initBookings());
        Map<String, Booking> allBookings = bookingsService.getAllBookings();
        assertTrue(allBookings.size() == NUM_BOOKINGS);
    }

    @Test
    public void testUpdateBooking() {
        when(mockBookingsRepository.findAllBookings()).thenReturn(initBookings());
        try {
            bookingsService.updateBooking("0", createBooking(USER_ID, FLIGHT_ID));
            assertEquals("user1", bookingsService.getAllBookings().get("0").getUser());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateNotFoundBooking() {
        when(mockBookingsRepository.findAllBookings()).thenReturn(initBookings());
        try {
            bookingsService.updateBooking("5", createBooking(USER_ID, FLIGHT_ID));
            fail("NotFoundException not thrown for not found booking!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteBooking() {
        when(mockBookingsRepository.findAllBookings()).thenReturn(initBookings());
        try {
            bookingsService.deleteBooking("0");
            assertNull(bookingsService.getAllBookings().get("0"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNotFoundBooking() {
        when(mockBookingsRepository.findAllBookings()).thenReturn(initBookings());
        try {
            bookingsService.deleteBooking("5");
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
