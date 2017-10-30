package com.lietsoft.bookingmanager.controller;

import com.lietsoft.bookingmanager.model.Flight;
import com.lietsoft.bookingmanager.model.Segment;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.service.FlightsService;
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

public class FlightsControllerTest {

    private static final int NUM_FLIGHTS = 2;

    private FlightsController controller;

    private FlightsService mockFlightsService;

    @Before
    public void setUp() {
        controller = new FlightsController();

        mockFlightsService = mock(FlightsService.class);
        controller.setFlightsService(mockFlightsService);
    }

    @Test
    public void testGetAllFlights() {
        when(mockFlightsService.getAllFlights()).thenReturn(initFlights());
        Response responseFlights = controller.getAllFlights();
        assertEquals(200, responseFlights.getStatus());
        assertTrue(((HashMap<String, Flight>)responseFlights.getEntity()).size() == NUM_FLIGHTS);
    }

    @Test
    public void testGetFlight() {
        try {
            when(mockFlightsService.getFlight(any())).thenReturn(createFlight("origin1", "destination1"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseFlights = controller.getFlight("1");
        assertEquals(200, responseFlights.getStatus());
        assertTrue("origin1".equals(((Flight)responseFlights.getEntity()).getInbound().getOrigin()));
    }

    @Test
    public void testGetNotFoundFlight() {
        try {
            Mockito.doThrow(new NotFoundException("Flight not found")).when(mockFlightsService).getFlight(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseFlights = controller.getFlight("1");
        assertEquals(404, responseFlights.getStatus());
    }

    @Test
    public void testCreateFlight() {
        Response responseFlights = controller.createFlight(createFlight("origin2", "destination2"));
        assertEquals(200, responseFlights.getStatus());
    }

    @Test
    public void testUpdateFlight() {
        Response responseFlights = controller.updateFlight("2", createFlight("origin2", "destination2"));
        assertEquals(200, responseFlights.getStatus());
    }

    @Test
    public void testUpdateNotFoundFlight() {
        try {
            Mockito.doThrow(new NotFoundException("Flight does not exist")).when(mockFlightsService).updateFlight(any(), any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseFlights = controller.updateFlight("2", createFlight("origin2", "destination2"));
        assertEquals(404, responseFlights.getStatus());
    }

    @Test
    public void testDeleteFlight() {
        Response responseFlights = controller.deleteFlight("2");
        assertEquals(200, responseFlights.getStatus());
    }

    @Test
    public void testDeleteNotFoundFlight() {
        try {
            Mockito.doThrow(new NotFoundException("Flight does not exist")).when(mockFlightsService).deleteFlight(any());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Response responseFlights = controller.deleteFlight("2");
        assertEquals(404, responseFlights.getStatus());
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
               new Segment(destination, origin, "5/1/3067", "3/12/3071"), 101.20, true);
    }

}
