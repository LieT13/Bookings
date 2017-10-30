package com.lietsoft.bookingmanager.model;

public class Booking {

    private String user;

    private String flight;

    public Booking() {}

    public Booking(String user, String flight) {
        this.user = user;
        this.flight = flight;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

}
