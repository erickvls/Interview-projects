package com.alten.booking.api.utils;

import com.alten.booking.api.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.alten.booking.api.utils.DateValidatorHelper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateValidatorHelperTest {

    @Test
    public void shouldConvertFromStringStartDateToLocalDateTime(){
        LocalDate startDate = LocalDate.now();
        LocalDateTime expectedResult = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime result = fromStringStartDateToLocalDate(startDate.toString());
        assertEquals(result,expectedResult);
    }

    @Test
    public void shouldThrowAnExceptionWhenConvertFromStringStartDateToLocalDateTime(){
        String exceptionMessage = "Invalid data format.";
        Exception exception = assertThrows(
                DataParserException.class,
                () ->  fromStringStartDateToLocalDate("jasndjasd"));
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldConvertFromStringEndDateToLocalDateTime(){
        LocalDate endDate = LocalDate.now();
        LocalDateTime expectedResult = endDate.atTime(23,59,59);
        LocalDateTime result = fromStringEndDateToLocalDate(endDate.toString());
        assertEquals(result,expectedResult);
    }

    @Test
    public void shouldThrowAnExceptionWhenConvertFromStringEndDateToLocalDateTime(){
        String exceptionMessage = "Invalid data format.";
        Exception exception = assertThrows(
                DataParserException.class,
                () ->  fromStringEndDateToLocalDate("jasndjasd"));
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldNotThrowAnyExceptionWhenStartDateAndEndDateAreOk(){
        LocalDateTime startDate = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3).withHour(23).withMinute(59).withSecond(59);
        Assertions.assertDoesNotThrow(() -> reservationTime(startDate,endDate));
    }

    @Test
    public void shouldThrownAnExceptionIfStartDateIsToday(){
        String exceptionMessage = "Start date must be in the future.";

        LocalDateTime startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3).withHour(23).withMinute(59).withSecond(59);

        Exception exception = assertThrows(
                DateAlreadyPassedException.class,
                () ->  reservationTime(startDate,endDate));
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldThrownAnExceptionIfStartDayIsAfterEndDate(){
        String exceptionMessage = "The start date can not be after end date.";

        LocalDateTime startDate = LocalDateTime.now().plusDays(5).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3).withHour(23).withMinute(59).withSecond(59);

        Exception exception = assertThrows(
                StartDateAfterEndDateException.class,
                () ->  reservationTime(startDate,endDate));
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldThrownAnExceptionIfStayIsLongerThan3Days(){
        String exceptionMessage = "The stay can not be longer than 3 days.";

        LocalDateTime startDate = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5).withHour(23).withMinute(59).withSecond(59);

        Exception exception = assertThrows(
                LongerThanThreeDaysException.class,
                () ->  reservationTime(startDate,endDate));
        assertEquals(exceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldThrownAnExceptionIfStayStartsAfter30Days(){
        String exceptionMessage = "It can't be reserved more than 30 days in advance";

        LocalDateTime startDate = LocalDateTime.now().plusDays(40).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().plusDays(41).withHour(23).withMinute(59).withSecond(59);

        Exception exception = assertThrows(
                MoreThanThirtyDaysException.class,
                () ->  reservationTime(startDate,endDate));
        assertEquals(exceptionMessage,exception.getMessage());
    }
}
