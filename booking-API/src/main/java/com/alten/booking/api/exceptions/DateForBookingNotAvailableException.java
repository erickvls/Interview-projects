package com.alten.booking.api.exceptions;



/**
 * Exception responsible for throw a message due there is no available dates for booking.
 */
public class DateForBookingNotAvailableException extends BookingException{
    public DateForBookingNotAvailableException() {
        super("The dates that you have selected is not available.");
    }
}
