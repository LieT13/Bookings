package com.lietsoft.bookings.repositories.impl;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.repositories.FlightsRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryFlightsRepository implements FlightsRepository {

    private static final Map<String, Flight> flights =  Collections.synchronizedMap(new HashMap<String, Flight>());

    @Override
    public Map<String, Flight> findAllFlights() {
        return flights;
    }

    @Override
    public Flight findFlightById(String id) throws NotFoundException {
        synchronized (flights) {
            if (flights.get(id) == null) {
                throw new NotFoundException("Flight does not exist!");
            }
            return flights.get(id);
        }
    }

    @Override
    public boolean flightExists(String id) {
        return flights.containsKey(id);
    }

    @Override
    public void addFlight(Flight flight) {
        flights.put(UUID.randomUUID().toString(), flight);
    }

    @Override
    public void updateFlight(String id, Flight flight) throws NotFoundException {
        synchronized (flights) {
            if (flights.get(id) == null) {
                throw new NotFoundException("Flight does not exist!");
            }
            flights.put(id, flight);
        }
    }

    @Override
    public void deleteFlight(String id) throws NotFoundException {
        synchronized (flights) {
            if (flights.get(id) == null) {
                throw new NotFoundException("Flight does not exist!");
            }
            flights.remove(id);
        }
    }

    public static void setFlights(HashMap<String, Flight> newFlights) {
        flights.clear();
        flights.putAll(newFlights);
    }

}
