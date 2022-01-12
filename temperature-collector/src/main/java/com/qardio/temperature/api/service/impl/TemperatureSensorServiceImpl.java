package com.qardio.temperature.api.service.impl;

import com.qardio.temperature.api.domain.model.TemperatureSensor;
import com.qardio.temperature.api.dto.TemperatureSensorRequest;
import com.qardio.temperature.api.repository.TemperatureRepository;
import com.qardio.temperature.api.service.TemperatureSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperatureSensorServiceImpl implements TemperatureSensorService {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureSensorServiceImpl(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @Transactional
    private TemperatureSensor save(TemperatureSensorRequest temperatureSensorRequest) {
        TemperatureSensor temperatureSensor = TemperatureSensor.builder()
                .temperatureInCelsius(temperatureSensorRequest.getTemperatureInCelsius())
                .epochDatetime(Instant.ofEpochSecond(temperatureSensorRequest.getEpochTimestamp()))
                .build();

        return temperatureRepository.save(temperatureSensor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTemperature(List<TemperatureSensorRequest> temperatureSensorRequest) {
        temperatureSensorRequest.forEach(this::save);
    }

}
