package com.alten.booking.api.service.impl;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.domain.Room;
import com.alten.booking.api.dtos.BookingRequest;
import com.alten.booking.api.dtos.RescheduleBookingRequest;
import com.alten.booking.api.exceptions.DateForBookingNotAvailableException;
import com.alten.booking.api.exceptions.NotFoundException;
import com.alten.booking.api.repository.BookingRepository;
import com.alten.booking.api.service.BookingService;
import com.alten.booking.api.service.RoomService;
import com.alten.booking.api.utils.DateValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

import static com.alten.booking.api.utils.DateValidatorHelper.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Booking createBooking(BookingRequest bookingRequest) {
        LocalDateTime startDate = fromStringStartDateToLocalDate(bookingRequest.getStartDate());
        LocalDateTime endDate = fromStringEndDateToLocalDate(bookingRequest.getEndDate());

        reservationTime(startDate, endDate);

        Room room = roomService.findById(bookingRequest.getRoom());
        validateBookingAvailable(room, startDate, endDate);

        Booking booking = Booking.builder().startDate(startDate)
                .endDate(endDate)
                .room(room)
                .build();
        return bookingRepository.save(booking);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking findBooking(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id: {%s} was not found", id)));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Booking cancelBooking(long bookingId) {
        Booking booking = findBookingByIdAndCancelledFlag(bookingId, Boolean.FALSE);
        booking.setCancelled(Boolean.TRUE);
        return bookingRepository.save(booking);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Booking updateBooking(long bookingId, RescheduleBookingRequest bookingRequest) {
        Booking booking = findBookingByIdAndCancelledFlag(bookingId, Boolean.FALSE);

        LocalDateTime newStartDate = fromStringStartDateToLocalDate(bookingRequest.getStartDate());
        LocalDateTime newEndDate = fromStringEndDateToLocalDate(bookingRequest.getEndDate());

        reservationTime(newStartDate, newEndDate);
        validateBookingAvailable(booking.getRoom(), newStartDate, newEndDate);

        booking.setStartDate(newStartDate);
        booking.setEndDate(newEndDate);
        return bookingRepository.save(booking);
    }

    /**
     * The method finds a booking by id with cancelled flag.
     * @param bookingId Booking id.
     * @param isCancelled True, for searching a booking cancelled. False, for searching a bookings not cancelled.
     * @throws NotFoundException If booking id provided is not found.
     * @return A booking.
     */
    private Booking findBookingByIdAndCancelledFlag(long bookingId, boolean isCancelled) {
        return bookingRepository.findByIdAndIsCancelled(bookingId, isCancelled)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id: {%s} was not found or it has been cancelled", bookingId)));
    }

    /**
     * The methods checks if there are dates available for a room through a range of date.
     * If list of bookings is not empty, so there are bookings for those dates.
     * If list is empty, so there are not bookings for those dates.
     * @param room Room id.
     * @param startDate check-in date (where range date begins).
     * @param endDate check-out date (where range date ends).
     * @throws DateForBookingNotAvailableException If there is not available dates for booking.
     */
    private void validateBookingAvailable(Room room, LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> bookings = bookingRepository.findBookingByDateRangeAndRoomAndNotCancelled(room, startDate, endDate);
        if (!bookings.isEmpty()) {
            throw new DateForBookingNotAvailableException();
        }
    }

}
