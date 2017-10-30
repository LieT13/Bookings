package com.lietsoft.bookings.repositories;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.Segment;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.impl.InMemoryFlightsRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryFlightsRepositoryTest {

    private static final long NUM_FLIGHTS = 2;
    private InMemoryFlightsRepository flightsRepository;

    @Before
    public void setUp() {
        flightsRepository = new InMemoryFlightsRepository();
    }

    @Test
    public void testFindAllFlights() {
        InMemoryFlightsRepository.setFlights(initFlights());
        Map<String, Flight> allFlights = flightsRepository.findAllFlights();
        assertTrue(allFlights.size() == NUM_FLIGHTS);
    }

    @Test
    public void testFindFlightById() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            Flight flight = flightsRepository.findFlightById("0");
            assertEquals("origin0", flight.getInbound().getOrigin());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent flight!");
        }
    }

    @Test
    public void testFindNotFoundFlightById() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            flightsRepository.findFlightById("5");
            fail("NotFoundException not thrown for not found flight!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testAddFlight() {
        InMemoryFlightsRepository.setFlights(initFlights());
        Flight df = createFlight("origin1", "destination1");
        flightsRepository.addFlight(df);
        assertTrue(flightsRepository.findAllFlights().values().contains(df));
    }

    @Test
    public void testUpdateFlight() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            flightsRepository.updateFlight("0", createFlight("origin1", "destination1"));
            assertEquals("origin1", flightsRepository.findAllFlights().get("0").getInbound().getOrigin());
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent flight!");
        }
    }

    @Test
    public void testUpdateNotFoundFlight() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            flightsRepository.updateFlight("5", createFlight("origin5", "destination5"));
            fail("NotFoundException not thrown for not found flight!");
        } catch (NotFoundException e) {
            // ok!
        }
    }

    @Test
    public void testDeleteFlight() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            flightsRepository.deleteFlight("0");
            assertNull(flightsRepository.findAllFlights().get("0"));
        } catch (NotFoundException e) {
            fail("NotFoundException thrown for existent flight!");
        }
    }

    @Test
    public void testDeleteNotFoundFlight() {
        InMemoryFlightsRepository.setFlights(initFlights());
        try {
            flightsRepository.deleteFlight("5");
            fail("NotFoundException not thrown for not found flight!");
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
