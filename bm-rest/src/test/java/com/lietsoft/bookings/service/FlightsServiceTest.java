package com.lietsoft.bookings.service;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.Segment;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.FlightsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlightsServiceTest {

    private static final int NUM_FLIGHTS = 2;

    private FlightsService flightsService;

    private FlightsRepository mockFlightsRepository;

    @Before
    public void setUp() {
        flightsService = new FlightsService();
        mockFlightsRepository = mock(FlightsRepository.class);
        flightsService.setFlightsRepository(mockFlightsRepository);
    }

    @Test
    public void testGetAllFlights() {
        when(mockFlightsRepository.findAllFlights()).thenReturn(initFlights());
        Map<String, Flight> allFlights = flightsService.getAllFlights();
        assertTrue(allFlights.size() == NUM_FLIGHTS);
    }

    @Test
    public void testUpdateFlight() {
        when(mockFlightsRepository.findAllFlights()).thenReturn(initFlights());
        try {
            flightsService.updateFlight("0", createFlight("origin0", "destination0"));
            Assert.assertEquals("origin0", flightsService.getAllFlights().get("0").getInbound().getOrigin());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateNotFoundFlight() {
        when(mockFlightsRepository.findAllFlights()).thenReturn(initFlights());
        try {
            flightsService.updateFlight("5", createFlight("origin0", "destination0"));
            fail("NotFoundException not thrown for not found flight!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteFlight() {
        when(mockFlightsRepository.findAllFlights()).thenReturn(initFlights());
        try {
            flightsService.deleteFlight("0");
            assertNull(flightsService.getAllFlights().get("0"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNotFoundFlight() {
        when(mockFlightsRepository.findAllFlights()).thenReturn(initFlights());
        try {
            flightsService.deleteFlight("5");
            fail("NotFoundException not thrown for not found user!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    private HashMap<String, Flight> initFlights() {
        HashMap<String, Flight> flights = new HashMap<>();
        for (long i = 0; i< NUM_FLIGHTS; i++) {
            flights.put(String.valueOf(i), createFlight("origin" + i, "destination" + i));
        }
        return flights;
    }

    private Flight createFlight(String origin, String destination) {
        return new Flight(new Segment(origin, destination, "8/3/3056", "12/30/3065"),
                new Segment(destination, origin, "5/1/3067", "3/12/3071"),
                101.20, true);
    }

}
