package com.alten.booking.api.service;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.domain.Room;
import com.alten.booking.api.exceptions.NotFoundException;
import com.alten.booking.api.repository.RoomRepository;
import com.alten.booking.api.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindARoomById(){
        Room room = Room.builder().id(1).price(new BigDecimal(150)).build();
        when(roomRepository.findById(1L)).thenReturn(Optional.ofNullable(room));
        Room result = roomService.findById(1);
        assertEquals(result,room);
    }

    @Test
    public void shouldThrowAnExceptionWhenRoomIsNotFound(){
        String notFoundMessage = "Room with id: {1} was not found";
        Exception exception = assertThrows(
                NotFoundException.class,
                () ->  roomService.findById(1));

        when(roomRepository.findById(1L)).thenThrow(NotFoundException.class);
        assertEquals(notFoundMessage,exception.getMessage());
    }

    @Test
    public void shouldReturnAListOfAvailableDates(){
        Room room = createRoomWithBookings();
        LocalDate checkIn = LocalDate.of(2021,1,1);
        LocalDate checkOut = LocalDate.of(2021,1,30);

        when(roomRepository.findById(any())).thenReturn(Optional.of(room));
        List<LocalDate> bookingAvailable = roomService.findAvailableDates(room.getId(),checkIn,checkOut);

        List<LocalDate> expectedDates = LocalDate.of(2021,1,4).datesUntil(checkOut.plusDays(1)).collect(Collectors.toList());

        assertEquals(bookingAvailable.size(),27);
        assertEquals(bookingAvailable,expectedDates);
    }

    private Room createRoomWithBookings(){
        Room room = Room.builder().id(1L).price(new BigDecimal(120)).build();
        Booking booking = Booking.builder().startDate(LocalDateTime.of(2021,1,1,00,00,00))
                .endDate(LocalDateTime.of(2021,1,3,23,59,59))
                .id(room.getId())
                .build();
        room.setBooking(Set.of(booking));
        return room;
    }
}
