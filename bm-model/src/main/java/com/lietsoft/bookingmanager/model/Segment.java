package com.lietsoft.bookingmanager.model;

public class Segment {

    private String origin;

    private String destination;

    private String departureDate;

    private String arrivalDate;

    public Segment() {}

    public Segment(String origin, String destination, String departureDate, String arrivalDate) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

}
