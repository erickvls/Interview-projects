package com.alten.booking.api.service;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.domain.Room;
import com.alten.booking.api.dtos.BookingRequest;
import com.alten.booking.api.dtos.RescheduleBookingRequest;
import com.alten.booking.api.exceptions.DateForBookingNotAvailableException;
import com.alten.booking.api.exceptions.NotFoundException;
import com.alten.booking.api.repository.BookingRepository;
import com.alten.booking.api.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateABookingWhenADateIsAvailable(){
        Room room = Room.builder().id(1L).price(new BigDecimal("120")).build();
        BookingRequest bookingRequest = new BookingRequest(LocalDate.now().plusDays(1).toString(),LocalDate.now().plusDays(2).toString(),1L);
        when(roomService.findById(1L)).thenReturn(room);

        bookingService.createBooking(bookingRequest);
        verify(bookingRepository).save(any());
    }

    @Test
    public void shouldThrowAnExceptionWhenADateIsNotAvailable(){

        String notAvailableDatesMessage = "The dates that you have selected is not available.";
        Room room = Room.builder().id(1L).price(new BigDecimal("120")).build();
        BookingRequest bookingRequest = new BookingRequest(LocalDate.now().plusDays(1).toString(), LocalDate.now().plusDays(3).toString(),1L);

        when(roomService.findById(1L)).thenReturn(room);
        when(bookingRepository.findBookingByDateRangeAndRoomAndNotCancelled(any(),any(),any())).thenReturn(Collections.singletonList(Booking.builder().build()));

        Exception exception = assertThrows(
                DateForBookingNotAvailableException.class,
                () ->  bookingService.createBooking(bookingRequest));

        assertEquals(notAvailableDatesMessage,exception.getMessage());
    }


    @Test
    public void shouldFindABookingWhenPassedAnId(){
        Room room = Room.builder().id(1L).price(new BigDecimal("120")).build();
        Booking booking = Booking.builder().startDate(LocalDateTime.of(2021,12,20,00,00,00))
                .endDate(LocalDateTime.of(2021,12,18,23,59,59))
                .id(1L)
                .room(room)
                .build();
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(booking));

        Booking bookingResult = bookingService.findBooking(1L);
        assertEquals(bookingResult,booking);
    }

    @Test
    public void shouldThrowAnExceptionWhenNotFindABookingById(){

        String notFoundMessage = "Booking with id: {1} was not found";
        Exception exception = assertThrows(
                NotFoundException.class,
                () ->  bookingService.findBooking(1L));

        when(bookingRepository.findById(1L)).thenThrow(NotFoundException.class);
        assertEquals(notFoundMessage,exception.getMessage());
    }



    @Test
    public void shouldCancelABookingWhenProvideABookingId(){
        Room room = Room.builder().id(1L).price(new BigDecimal("120")).build();
        Booking booking = Booking.builder().startDate(LocalDateTime.of(2021,12,20,00,00,00))
                .endDate(LocalDateTime.of(2021,12,18,23,59,59))
                .id(1L)
                .room(room)
                .build();
        when(bookingRepository.findByIdAndIsCancelled(1L, Boolean.FALSE)).thenReturn(Optional.ofNullable(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        Booking bookingResult = bookingService.cancelBooking(1L);
        assertTrue(bookingResult.isCancelled());
    }

    @Test
    public void shouldThrowAnExceptionWhenCancelABookingWithNotFoundId(){
        String notFoundMessage = "Booking with id: {1} was not found or it has been cancelled";

        Exception exception = assertThrows(
                NotFoundException.class,
                () ->  bookingService.cancelBooking(1L));

        when(bookingRepository.findByIdAndIsCancelled(1L, false)).thenThrow(NotFoundException.class);

        assertEquals(notFoundMessage,exception.getMessage());
    }

    @Test
    public void shouldUpdateABookingWhenProvideNewDates(){
        Room room = Room.builder().id(1L).price(new BigDecimal("120")).build();
        Booking booking = Booking.builder().startDate(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0))
                .endDate(LocalDateTime.now().plusDays(2).withHour(23).withMinute(59).withSecond(59))
                .id(1L)
                .room(room)
                .build();
        String newStartDate = LocalDate.now().plusDays(10).toString();
        String newEndDate = LocalDate.now().plusDays(12).toString();
        RescheduleBookingRequest rescheduleBookingRequest = new RescheduleBookingRequest(newStartDate,newEndDate);
        when(bookingRepository.findByIdAndIsCancelled(1L,false)).thenReturn(Optional.ofNullable(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        Booking bookingResult = bookingService.updateBooking(1L,rescheduleBookingRequest);
        assertEquals(bookingResult.getStartDate().toLocalDate().toString(),newStartDate);
        assertEquals(bookingResult.getEndDate().toLocalDate().toString(),newEndDate);
    }
}
