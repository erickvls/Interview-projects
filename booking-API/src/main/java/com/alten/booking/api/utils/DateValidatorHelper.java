package com.alten.booking.api.utils;

import com.alten.booking.api.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.alten.booking.api.Constants.BOOKING_LIMIT_IN_ADVANCE;
import static com.alten.booking.api.Constants.STAY_LIMIT;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Helper class to validate dates.
 */
public final class DateValidatorHelper {
    private DateValidatorHelper() {
    }

    /**
     * The method checks if dates are valid.
     * @param startDate check-in .
     * @param endDate   check-out.
     * @throws StartDateAfterEndDateException If start date is after than end date.
     * @throws DateAlreadyPassedException If start date is in the past.
     */
    public static void reservationTime(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException();
        }
        if (LocalDateTime.now().isAfter(startDate)) {
            throw new DateAlreadyPassedException();
        }
        checkStayLongerThanThreeDays(startDate, endDate);
        checkReservationDate30DaysInAdvance(startDate);
    }

    /**
     * The method converts a string a startDate to LocalDateTime.
     * @param startDate check-in date (format:YYYY-MM-DD).
     * @throws DataParserException if data provided is not a data format.
     * @return A LocalDateTime from String.
     */
    public static LocalDateTime fromStringStartDateToLocalDate(String startDate){
         try{
             return LocalDate.parse(startDate).atStartOfDay();
         } catch (Exception ex){
             throw new DataParserException();
         }

    }

    /**
     * The method converts a string a endDate to LocalDateTime.
     * @param endDate check-out date (format:YYYY-MM-DD).
     * @throws DataParserException if data provided is not a data format.
     * @return A LocalDateTime from String.
     */
    public static LocalDateTime fromStringEndDateToLocalDate(String endDate){
        try{
            return LocalDate.parse(endDate).atTime(23,59,59);
        } catch (Exception ex){
            throw new DataParserException();
        }
    }

    /**
     * The method checks for the dates provided if the stay is longer than 3 days.
     * The <a href="https://docs.oracle.com/javase/8/docs/api/java/time/temporal/ChronoUnit.html#between-java.time.temporal.Temporal-java.time.temporal.Temporal-">JavaDoc</a>
     * says that using between, the end date is not inclusive. So the end date is not included
     * in the calculation. For this purpose, it was necessary add + 1.
     * @throws LongerThanThreeDaysException If the stay is greater than 3 days.
     * @param startDate
     * @param endDate
     */
    private static void checkStayLongerThanThreeDays(LocalDateTime startDate, LocalDateTime endDate) {
        long daysBetween = DAYS.between(startDate, endDate) + 1;
        if (daysBetween > STAY_LIMIT) {
            throw new LongerThanThreeDaysException();
        }
    }

    /**
     * The method checks if a booking is done 30 days in advance.
     * @throws MoreThanThirtyDaysException If the booking is done more than 30 days in advance.
     * @param startDate check-in.
     */
    private static void checkReservationDate30DaysInAdvance(LocalDateTime startDate){
        LocalDateTime today = LocalDateTime.now();
        long daysBetween = DAYS.between(today,startDate) + 1;
        if(daysBetween > BOOKING_LIMIT_IN_ADVANCE){
            throw new MoreThanThirtyDaysException();
        }
    }
}
