package com.lietsoft.bookingmanager.model.exception;

public abstract class BookingManagerException extends Exception {

    public abstract int getStatus();

    public BookingManagerException(String message) {
        super(message);
    }

}
