package com.lietsoft.bookingmanager.model.exception;

public class NotFoundException extends BookingManagerException {

    public NotFoundException(String message) {
        super(message);
    }

    public int getStatus() {
        return 404;
    }

}
