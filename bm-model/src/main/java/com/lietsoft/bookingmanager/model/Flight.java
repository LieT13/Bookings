package com.lietsoft.bookingmanager.model;

public class Flight {

    private Segment inbound;

    private Segment outbound;

    private Double price;

    private Boolean offer;

    public Flight() {}

    public Flight(Segment inbound, Segment outbound, Double price, Boolean offer) {
        this.inbound = inbound;
        this.outbound = outbound;
        this.price = price;
        this.offer = offer;
    }

    public Segment getInbound() {
        return inbound;
    }

    public void setInbound(Segment inbound) {
        this.inbound = inbound;
    }

    public Segment getOutbound() {
        return outbound;
    }

    public void setOutbound(Segment outbound) {
        this.outbound = outbound;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isOffer() {
        return offer;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

}
