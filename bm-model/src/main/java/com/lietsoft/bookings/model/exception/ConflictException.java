package com.lietsoft.bookings.model.exception;

public class ConflictException extends BookingsException {

    public ConflictException(String message) {
        super(message);
    }

    public int getStatus() {
        return 409;
    }

}
