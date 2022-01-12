package com.alten.booking.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO class for reading JSON request to perform a booking.
 * startDate receives a string date that user wants to do a check-in. Format: YYYY-MM-DD.
 * endDate receives a string date that user wants to do a check-out. Format: YYYY-MM-DD.
 * room is a roomId that user wants to reserve.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotEmpty(message = "Start date can not be empty.")
    private String startDate;
    @NotEmpty(message = "End date can not be empty.")
    private String endDate;
    @NotNull
    private long room;
}
