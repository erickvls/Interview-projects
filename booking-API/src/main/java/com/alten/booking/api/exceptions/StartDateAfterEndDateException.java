package com.alten.booking.api.exceptions;

/**
 * Exception responsible for throw a message when a start date is after a end date.
 */
public class StartDateAfterEndDateException extends BookingException {
    public StartDateAfterEndDateException() {
        super("The start date can not be after end date.");
    }
}
