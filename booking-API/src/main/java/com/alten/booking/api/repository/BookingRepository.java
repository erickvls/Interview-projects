package com.alten.booking.api.repository;

import com.alten.booking.api.domain.Booking;
import com.alten.booking.api.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The repository for Booking to retrieve data using CrudRepository.
 */
@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    /**
     * The method finds a list of bookings that has been reserved for a date range for a specific room which were not cancelled.
     * @param room roomId
     * @param startDate check-in (startDate)
     * @param endDate check-out (endDate)
     * @return A list of Bookings that has been already reserved.
     */
    @Query(value = "SELECT b FROM Booking b " +
            "WHERE b.room = ?1 AND " +
            "(?2 BETWEEN b.startDate AND b.endDate) OR " +
            "(?3 BETWEEN b.startDate AND b.endDate) OR " +
            "(?2 <= b.startDate AND ?3 >= b.endDate) AND " +
            "b.isCancelled is false")
    List<Booking> findBookingByDateRangeAndRoomAndNotCancelled(Room room, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * The method returns a optional of Booking.
     * It finds a booking by id and isCancelled flag.
     * @param id Booking id
     * @param isCancelled true, if user wants retrieve a booking that is cancelled, false if not.
     * @return Optional of Booking.
     */
    Optional<Booking> findByIdAndIsCancelled(long id, boolean isCancelled);
}
