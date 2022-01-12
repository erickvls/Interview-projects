package com.alten.booking.api.service;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.dtos.BookingRequest;
import com.alten.booking.api.dtos.RescheduleBookingRequest;
import com.alten.booking.api.exceptions.*;

/**
 * A service class for Booking.
 */
public interface BookingService {

    /**
     * The method allows to create a booking.
     * @param bookingRequest It is a DTO that receives from a request a startDate,endDate and roomId.
     * @return A booking that has been created.
     */
    Booking createBooking(BookingRequest bookingRequest);

    /**
     * The method update a booking.
     * @param bookingId Booking id.
     * @param rescheduleBookingRequest It is a DTO in charge to receive the new dates that user wants to update.
     * @return A booking updated.
     */
    Booking updateBooking(long bookingId, RescheduleBookingRequest rescheduleBookingRequest);

    /**
     * The method finds a booking.
     * @param id Booking id.
     * @throws NotFoundException If a booking is not found.
     * @return A booking if it is found.
     */
    Booking findBooking(long id);

    /**
     * The method cancel a booking.
     * @param id Booking id.
     * @return A booking with status changed to cancelled.
     */
    Booking cancelBooking(long id);
}
