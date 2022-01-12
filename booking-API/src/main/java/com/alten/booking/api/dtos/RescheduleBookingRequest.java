package com.alten.booking.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * DTO class responsible to read a json with new dates.
 * startDate receives a string new date for which user wants to reschedule . Format: YYYY-MM-DD.
 * endDate receives a string new date for which user wants to reschedule . Format: YYYY-MM-DD.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleBookingRequest {
    @NotEmpty(message = "Start date can not be empty.")
    private String startDate;
    @NotEmpty(message = "End date can not be empty.")
    private String endDate;
}
