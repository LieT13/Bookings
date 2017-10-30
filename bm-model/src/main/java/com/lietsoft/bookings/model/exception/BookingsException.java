package com.lietsoft.bookings.model.exception;

public abstract class BookingsException extends Exception {

    public abstract int getStatus();

    public BookingsException(String message) {
        super(message);
    }

}
