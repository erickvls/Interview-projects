package com.alten.booking.api.service;

import com.alten.booking.api.domain.Room;
import com.alten.booking.api.exceptions.NotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * A service class for Room.
 */
public interface RoomService {

    /**
     * The method finds a room by id.
     * @param id RoomId
     * @throws NotFoundException If room is not found.
     * @return A room, if a room is found.
     */
    Room findById(long id);

    /**
     * The method finds all available dates given a date range for a specific room.
     * @param id RoomId.
     * @param startDate check-in date (where range date begins).
     * @param endDate   check-out date (where range date ends).
     * @return A List of available dates for booking.
     */
    List<LocalDate> findAvailableDates(long id, LocalDate startDate , LocalDate endDate);
}
