package com.alten.booking.api.service.impl;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.domain.Room;
import com.alten.booking.api.exceptions.NotFoundException;
import com.alten.booking.api.repository.RoomRepository;
import com.alten.booking.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room findById(long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Room with id: {%s} was not found", id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalDate> findAvailableDates(long roomId, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> rangeDates = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
        Room room = findById(roomId);
        List<Booking> bookingList = new ArrayList<>(room.getBooking());

        for (Booking booking : bookingList) {
            LocalDate start = booking.getStartDate().toLocalDate();
            LocalDate end = booking.getEndDate().plusDays(1).toLocalDate();
            List<LocalDate> rangeDatesNotAvailable = start.datesUntil(end).collect(Collectors.toList());
            rangeDates.removeAll(rangeDatesNotAvailable);
        }
        return rangeDates;
    }
}
