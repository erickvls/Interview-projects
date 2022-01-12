package com.qardio.temperature.api.service;

import com.qardio.temperature.api.dto.TemperatureSensorResponse;

import java.util.List;

/**
 * A service class for Report.
 */
public interface ReportService {
    /**
     * It generates the report filtered with start and end time.
     * @param startTime Start time in epoch timestamp.
     * @param endTime End time in epoch timestamp.
     * @return A DTO with a list of temperatures.
     */
    List<TemperatureSensorResponse> getDataReport(long startTime, long endTime);
}
