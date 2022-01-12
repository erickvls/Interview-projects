package com.alten.booking.api.exceptions;

/**
 * Abstract Booking exception that uses RunTimeException.
 * All children classes will extend from BookingException for throw it with a message.
 */
public abstract class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}
