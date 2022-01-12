package com.qardio.temperature.api.service.impl;

import com.qardio.temperature.api.domain.model.TemperatureSensor;
import com.qardio.temperature.api.dto.TemperatureSensorResponse;
import com.qardio.temperature.api.repository.TemperatureRepository;
import com.qardio.temperature.api.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public ReportServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "temperatures", key = "{#startTime, #endTime}")
    public List<TemperatureSensorResponse> getDataReport(long startTime, long endTime) {
        Instant start = Instant.ofEpochSecond(startTime);
        Instant end = Instant.ofEpochSecond(endTime);
        List<TemperatureSensor> query = temperatureRepository.findByEpochDatetimeGreaterThanEqualAndEpochDatetimeLessThanEqual(start, end);
        return query.stream()
                .map(temperatureResult -> TemperatureSensorResponse.builder().temperatureInCelsius(temperatureResult.getTemperatureInCelsius())
                        .epochTimestamp(temperatureResult.getEpochDatetime().getEpochSecond())
                        .temperatureInFahrenheit((temperatureResult.getTemperatureInCelsius() * 1.8) + 32)
                        .build()).collect(Collectors.toList());
    }
}
