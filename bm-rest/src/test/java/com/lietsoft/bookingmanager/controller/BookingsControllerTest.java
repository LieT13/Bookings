package com.lietsoft.bookingmanager.controller;

import com.lietsoft.bookingmanager.model.Booking;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.service.BookingsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingsControllerTest {

    private static final int NUM_BOOKINGS = 2;

    private static final String FLIGHT_ID = "flight1";

    private BookingsController controller;

    private BookingsService mockBookingsService;

    @Before
    public void setUp() {
        controller = new BookingsController();

        mockBookingsService = mock(BookingsService.class);
        controller.setBookingsService(mockBookingsService);
    }

    @Test
    public void testGetAllBookings() {
        when(mockBookingsService.getAllBookings()).thenReturn(initBookings());
        Response responseBookings = controller.getAllBookings();
        assertEquals(200, responseBookings.getStatus());
        assertTrue(((HashMap<String, Booking>)responseBookings.getEntity()).size() == NUM_BOOKINGS);
    }

    @Test
    public void testGetBooking() {
        try {
            when(mockBookingsService.getBooking(any())).thenReturn(createBooking("user1", FLIGHT_ID));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseBookings = controller.getBooking("1");
        assertEquals(200, responseBookings.getStatus());
        assertTrue("user1".equals(((Booking)responseBookings.getEntity()).getUser()));
    }

    @Test
    public void testGetNotFoundBooking() {
        try {
            Mockito.doThrow(new NotFoundException("Booking not found")).when(mockBookingsService).getBooking(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseBookings = controller.getBooking("1");
        assertEquals(404, responseBookings.getStatus());
    }

    @Test
    public void testCreateBooking() {
        Response responseBookings = controller.createBooking(createBooking("user1", FLIGHT_ID));
        assertEquals(200, responseBookings.getStatus());
    }

    @Test
    public void testUpdateBooking() {
        Response responseBookings = controller.updateBooking("2", createBooking("user1", FLIGHT_ID));
        assertEquals(200, responseBookings.getStatus());
    }

    @Test
    public void testUpdateNotFoundBooking() {
        try {
            Mockito.doThrow(new NotFoundException("Booking does not exist")).when(mockBookingsService).updateBooking(any(), any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseBookings = controller.updateBooking("2", createBooking("user1", FLIGHT_ID));
        assertEquals(404, responseBookings.getStatus());
    }

    @Test
    public void testDeleteBooking() {
        Response responseBookings = controller.deleteBooking("2");
        assertEquals(200, responseBookings.getStatus());
    }

    @Test
    public void testDeleteNotFoundBooking() {
        try {
            Mockito.doThrow(new NotFoundException("Booking does not exist")).when(mockBookingsService).deleteBooking(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseBookings = controller.deleteBooking("2");
        assertEquals(404, responseBookings.getStatus());
    }

    private HashMap<String, Booking> initBookings() {
        HashMap<String, Booking> bookings = new HashMap<>();
        for (long i = 0; i< NUM_BOOKINGS; i++) {
            bookings.put(String.valueOf(i), createBooking("user" + i, "address" + i));
        }
        return bookings;
    }

    private Booking createBooking(String userId, String flightId) {
        return new Booking(userId, flightId);
    }

}
