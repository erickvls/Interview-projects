package com.qardio.temperature.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to return temperature reporting data as HTTP response.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureSensorResponse {
    private long epochTimestamp;
    private double temperatureInCelsius;
    private double temperatureInFahrenheit;
}
