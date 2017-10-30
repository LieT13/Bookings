package com.lietsoft.bookingmanager.controller;

import com.lietsoft.bookingmanager.model.Booking;
import com.lietsoft.bookingmanager.model.exception.BookingManagerException;
import com.lietsoft.bookingmanager.service.BookingsService;
import com.lietsoft.bookingmanager.model.ErrorResponse;
import com.lietsoft.bookingmanager.model.exception.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bookings/")
public class BookingsController {

    private BookingsService bookingsService;

    public BookingsController() {
        bookingsService = new BookingsService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        return Response.ok(bookingsService.getAllBookings()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("id") String id) {
        try {
            return Response.ok(bookingsService.getBooking(id)).build();
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(Booking booking) {
        bookingsService.addBooking(booking);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") String id, Booking booking) {
        try {
            bookingsService.updateBooking(id, booking);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") String id) {
        try {
            bookingsService.deleteBooking(id);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    private Response buildErrorResponse(BookingManagerException e) {
        int status = e.getStatus();
        return Response.status(Response.Status.fromStatusCode(status)).entity(new ErrorResponse(status, e.getMessage())).build();
    }

    public void setBookingsService(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

}
