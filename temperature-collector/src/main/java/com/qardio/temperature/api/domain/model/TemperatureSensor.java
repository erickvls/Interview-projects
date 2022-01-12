package com.qardio.temperature.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * Model class to save and read persisted data from db.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temperature_sensor")
public class TemperatureSensor {
    @Id
    private Instant epochDatetime;
    private double temperatureInCelsius;
}
