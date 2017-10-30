package com.lietsoft.bookingmanager.service;

import com.lietsoft.bookingmanager.model.Segment;
import com.lietsoft.bookingmanager.model.Flight;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;
import com.lietsoft.bookingmanager.repositories.FlightsRepository;
import com.lietsoft.bookingmanager.repositories.impl.InMemoryFlightsRepository;

import java.util.Map;

public class FlightsService {

    private FlightsRepository flightsRepository;

    public FlightsService() {
        flightsRepository = new InMemoryFlightsRepository();
    }

    public Map<String, Flight> getAllFlights() {
        return flightsRepository.findAllFlights();
    }

    public Flight getFlight(String flightId) throws NotFoundException {
        if (!flightsRepository.flightExists(flightId)) {
            throw new NotFoundException("Flight does not exist");
        }
        return flightsRepository.findFlightById(flightId);
    }

    public void addFlight(Flight flight) {
        normalize(flight);
        flightsRepository.addFlight(flight);
    }

    public void updateFlight(String flightId, Flight flight) throws NotFoundException {
        if (!flightsRepository.flightExists(flightId)) {
            throw new NotFoundException("Flight does not exist");
        }
        Flight oldFligh = flightsRepository.findFlightById(flightId);
        updateFlightData(oldFligh, flight);
    }

    public void deleteFlight(String flightId) throws NotFoundException {
        if (!flightsRepository.flightExists(flightId)) {
            throw new NotFoundException("Flight does not exist");
        }
        flightsRepository.deleteFlight(flightId);
    }

    private void normalize(Flight flight) {
        flight.setOffer(flight.isOffer() == null ? false: flight.isOffer());
    }

    private void updateFlightData(Flight oldFlight, Flight flight) {
        if (flight.getInbound() != null) {
            updateSegment(oldFlight.getInbound(), flight.getInbound());
        }
        if (flight.getOutbound() != null) {
            updateSegment(oldFlight.getOutbound(), flight.getOutbound());
        }
        if (flight.getPrice() != null) {
            oldFlight.setPrice(flight.getPrice());
        }
        if (flight.isOffer() != null) {
            oldFlight.setOffer(flight.isOffer());
        }
    }

    private void updateSegment(Segment oldSegment, Segment segment) {
        if (segment.getOrigin() != null) {
            oldSegment.setOrigin(segment.getOrigin());
        }
        if (segment.getDestination() != null) {
            oldSegment.setDestination(segment.getDestination());
        }
        if (segment.getDepartureDate() != null) {
            oldSegment.setDepartureDate(segment.getDepartureDate());
        }
        if (segment.getArrivalDate() != null) {
            oldSegment.setOrigin(segment.getArrivalDate());
        }
    }

    public void setFlightsRepository(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

}
