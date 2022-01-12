package com.alten.booking.api.exceptions;

/**
 * Exception responsible for throwing a message due a invalid data format.
 */
public class DataParserException extends BookingException{
    public DataParserException() {
        super("Invalid data format.");
    }
}
