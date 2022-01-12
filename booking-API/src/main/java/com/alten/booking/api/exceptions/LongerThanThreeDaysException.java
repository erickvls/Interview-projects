package com.alten.booking.api.exceptions;


/**
 * Exception responsible for throw a message due booking stay be longer than 3 days.
 */
public class LongerThanThreeDaysException extends BookingException {
    public LongerThanThreeDaysException() {
        super("The stay can not be longer than 3 days.");
    }

}
