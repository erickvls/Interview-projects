package com.qardio.temperature.api;

import com.qardio.temperature.api.domain.model.TemperatureSensor;
import com.qardio.temperature.api.dto.TemperatureSensorRequest;
import com.qardio.temperature.api.repository.TemperatureRepository;
import com.qardio.temperature.api.service.impl.TemperatureSensorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class TemperatureServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private TemperatureSensorServiceImpl temperatureSensorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendTemperatureWhenReceiveATemperature(){
        TemperatureSensorRequest t1 = TemperatureSensorRequest.builder().epochTimestamp(1632385894).temperatureInCelsius(20).build();
        TemperatureSensor temperatureSensor = TemperatureSensor.builder().epochDatetime(Instant.ofEpochSecond(t1.getEpochTimestamp())).temperatureInCelsius(t1.getTemperatureInCelsius()).build();
        List<TemperatureSensorRequest> requests = Collections.singletonList(t1);

        when(temperatureRepository.save(any(TemperatureSensor.class))).thenReturn(temperatureSensor);
        temperatureSensorService.sendTemperature(requests);

        verify(temperatureRepository,times(1)).save(temperatureSensor);
    }

    @Test
    public void shouldSendTemperatureWhenReceiveAListOfTemperatures(){
        TemperatureSensorRequest t1 = TemperatureSensorRequest.builder().epochTimestamp(1632385894).temperatureInCelsius(20).build();
        TemperatureSensorRequest t2 = TemperatureSensorRequest.builder().epochTimestamp(1632385894).temperatureInCelsius(32).build();
        TemperatureSensorRequest t3 = TemperatureSensorRequest.builder().epochTimestamp(1632385894).temperatureInCelsius(27).build();

        List<TemperatureSensorRequest> requests = Arrays.asList(t1,t2,t3);

        when(temperatureRepository.save(any())).thenReturn(any());
        temperatureSensorService.sendTemperature(requests);

        verify(temperatureRepository,times(3)).save(any());
    }
}

