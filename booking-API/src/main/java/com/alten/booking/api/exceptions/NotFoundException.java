package com.alten.booking.api.exceptions;

/**
 * Exception responsible for throw a message due a not found resource.
 */
public class NotFoundException extends BookingException {

    public NotFoundException(String message) {
        super(message);
    }

}
