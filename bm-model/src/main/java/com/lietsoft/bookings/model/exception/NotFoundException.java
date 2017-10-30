package com.lietsoft.bookings.model.exception;

public class NotFoundException extends BookingsException {

    public NotFoundException(String message) {
        super(message);
    }

    public int getStatus() {
        return 404;
    }

}
