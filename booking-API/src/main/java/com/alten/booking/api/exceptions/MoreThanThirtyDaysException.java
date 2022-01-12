package com.alten.booking.api.exceptions;

/**
 * Exception responsible for throw a message due a booking be longer than 30 days after current date.
 */
public class MoreThanThirtyDaysException extends BookingException {
    public MoreThanThirtyDaysException() {
        super("It can't be reserved more than 30 days in advance");
    }
}
