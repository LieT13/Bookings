package com.lietsoft.bookingmanager.model.exception;

public class ConflictException extends BookingManagerException {

    public ConflictException(String message) {
        super(message);
    }

    public int getStatus() {
        return 409;
    }

}
