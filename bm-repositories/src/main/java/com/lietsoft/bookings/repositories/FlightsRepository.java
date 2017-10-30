package com.lietsoft.bookings.repositories;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.exception.NotFoundException;

import java.util.Map;

public interface FlightsRepository {

    Map<String, Flight> findAllFlights();

    Flight findFlightById(String id) throws NotFoundException;

    boolean flightExists(String id);

    void addFlight(Flight user);

    void updateFlight(String id, Flight user) throws NotFoundException;

    void deleteFlight(String id) throws NotFoundException;

}
