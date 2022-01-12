package com.qardio.temperature.api.repository;

import com.qardio.temperature.api.domain.model.TemperatureSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * The repository for TemperatureSensor to retrieve data using CrudRepository.
 */
@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureSensor,Instant> {
    List<TemperatureSensor> findByEpochDatetimeGreaterThanEqualAndEpochDatetimeLessThanEqual(Instant fromData, Instant toDate);
}
