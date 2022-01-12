package com.alten.booking.api.exceptions;

/**
 * Exception responsible for throw a message due to the date provided being before the current date.
 */
public class DateAlreadyPassedException extends BookingException {
    public DateAlreadyPassedException() {
        super("Start date must be in the future.");
    }
}
