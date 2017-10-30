package com.lietsoft.bookingmanager.model;

public class Discount {

    private String city;

    private int discount;

    public Discount() {}

    public Discount(String city, int discount) {
        this.city = city;
        this.discount = discount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}
