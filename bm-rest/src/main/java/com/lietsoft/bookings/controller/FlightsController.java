package com.lietsoft.bookings.controller;

import com.lietsoft.bookings.model.Flight;
import com.lietsoft.bookings.model.exception.BookingsException;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.model.ErrorResponse;
import com.lietsoft.bookings.service.FlightsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/flights/")
public class FlightsController {

    private FlightsService flightsService;

    public FlightsController() {
        flightsService = new FlightsService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFlights() {
        return Response.ok(flightsService.getAllFlights()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlight(@PathParam("id") String id) {
        try {
            return Response.ok(flightsService.getFlight(id)).build();
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFlight(Flight flight) {
        flightsService.addFlight(flight);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFlight(@PathParam("id") String id, Flight flight) {
        try {
            flightsService.updateFlight(id, flight);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFlight(@PathParam("id") String id) {
        try {
            flightsService.deleteFlight(id);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    private Response buildErrorResponse(BookingsException e) {
        int status = e.getStatus();
        return Response.status(Response.Status.fromStatusCode(status)).entity(new ErrorResponse(status, e.getMessage())).build();
    }

    public void setFlightsService(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

}
