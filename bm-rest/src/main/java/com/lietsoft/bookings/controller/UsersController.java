package com.lietsoft.bookings.controller;

import com.lietsoft.bookings.model.exception.BookingsException;
import com.lietsoft.bookings.model.exception.NotFoundException;
import com.lietsoft.bookings.service.UsersService;
import com.lietsoft.bookings.model.ErrorResponse;
import com.lietsoft.bookings.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/")
public class UsersController {

    private UsersService usersService;

    public UsersController() {
        usersService = new UsersService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(usersService.getAllUsers()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        try {
            return Response.ok(usersService.getUser(id)).build();
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        usersService.addUser(user);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") String id, User user) {
        try {
            usersService.updateUser(id, user);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String id) {
        try {
            usersService.deleteUser(id);
        } catch (NotFoundException e) {
            return buildErrorResponse(e);
        }
        return Response.ok().build();
    }

    private Response buildErrorResponse(BookingsException e) {
        int status = e.getStatus();
        return Response.status(Response.Status.fromStatusCode(status)).entity(new ErrorResponse(status, e.getMessage())).build();
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

}
