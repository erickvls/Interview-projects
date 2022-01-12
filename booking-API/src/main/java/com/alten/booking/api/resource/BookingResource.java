package com.alten.booking.api.resource;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.dtos.BookingRequest;
import com.alten.booking.api.dtos.RescheduleBookingRequest;
import com.alten.booking.api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The class provide API endpoint for Booking such as:
 * Create a booking, find, cancel and update it.
 */
@RestController
@RequestMapping(value = "api/v1/booking/")
public class BookingResource {

    private final BookingService bookingService;

    @Autowired
    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * The method allows a user create a booking
     * @param bookingRequest It is a DTO that POST method requires to send a request
     * @return A HttpStatus code OK with Booking body if request has been processed correctly.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Booking> create(@RequestBody @Valid BookingRequest bookingRequest) {
        return new ResponseEntity<>(bookingService.createBooking(bookingRequest), HttpStatus.CREATED);
    }

    /**
     * The method allows find a booking providing a id.
     * @param id Booking id
     * @return A httpStatus code OK with booking found. If no booking is found, a notFoundException will be thrown.
     */
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<Booking> find(@PathVariable long id) {
        return new ResponseEntity<>(bookingService.findBooking(id), HttpStatus.OK);
    }

    /**
     * The method allows cancel a booking
     * @param id Booking id.
     * @return  A httpStatus code OK if the request was processed correctly.
     */

    @PatchMapping(value = "{id}/cancel", produces = "application/json")
    public ResponseEntity<Booking> cancel(@PathVariable long id) {
        return new ResponseEntity<>(bookingService.cancelBooking(id), HttpStatus.OK);
    }

    /**
     * The method allows to update a booking that was already created.
     * @param id Booking id
     * @param rescheduleBookingRequest It is a DTO in charge to receive the new dates that user wants to update.
     * @return  A httpStatus code OK with booking updated.
     */
    @PatchMapping("{id}/update")
    public ResponseEntity<Booking> update(@PathVariable long id, @Valid @RequestBody RescheduleBookingRequest rescheduleBookingRequest) {
        return new ResponseEntity<>(bookingService.updateBooking(id, rescheduleBookingRequest), HttpStatus.OK);
    }
}
