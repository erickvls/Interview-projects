package com.qardio.temperature.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * DTO class to read JSON HTTP request body.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureSensorRequest {

    @NotEmpty(message = "Value can not be empty")
    private double temperatureInCelsius;
    @NotEmpty(message = "Value can not be empty")
    private long epochTimestamp;
}
