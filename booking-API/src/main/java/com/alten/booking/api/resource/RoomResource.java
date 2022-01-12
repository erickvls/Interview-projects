package com.alten.booking.api.resource;

import com.alten.booking.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.alten.booking.api.Constants.DATE_FORMAT;

@RestController
@RequestMapping(value = "api/v1/room")
public class RoomResource {

    private final RoomService roomService;

    @Autowired
    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * The method allows to check the room availability for certain dates.
     * @param id RoomId.
     * @param startDate check-in date (where range date begins).
     * @param endDate   check-out date (where range date ends).
     * @return A List of available dates for that room.
     */
    @GetMapping(value = "{id}/availability", produces = "application/json")
    public ResponseEntity<List<LocalDate>> findAvailabilityRoom(@PathVariable long id,
                                                                @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate startDate,
                                                                @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate endDate) {
        return new ResponseEntity<>(roomService.findAvailableDates(id, startDate, endDate), HttpStatus.OK);
    }


}
