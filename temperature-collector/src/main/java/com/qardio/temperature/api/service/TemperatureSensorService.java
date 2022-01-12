package com.qardio.temperature.api.service;

import com.qardio.temperature.api.dto.TemperatureSensorRequest;

import java.util.List;

/**
 * A service class for collect the temperature(s) data and save into db.
 */
public interface TemperatureSensorService {
    /**
     * Read temperature data and save into database.
     * @param temperatureSensorRequest A list of temperatures in a bulk, if there is only one, so
     *                                 it will receive just one data in an array.
     */
    void sendTemperature(List<TemperatureSensorRequest> temperatureSensorRequest);
}
