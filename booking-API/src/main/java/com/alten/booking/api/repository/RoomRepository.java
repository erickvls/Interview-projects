package com.alten.booking.api.repository;

import com.alten.booking.api.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository for Room to retrieve data using CrudRepository.
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
}
