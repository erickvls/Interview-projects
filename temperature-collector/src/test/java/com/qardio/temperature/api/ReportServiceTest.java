package com.qardio.temperature.api;

import com.qardio.temperature.api.domain.model.TemperatureSensor;
import com.qardio.temperature.api.dto.TemperatureSensorResponse;
import com.qardio.temperature.api.repository.TemperatureRepository;
import com.qardio.temperature.api.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReportServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnReportWithDataWhenPassedDateRange(){
        Instant instant = Instant.ofEpochSecond(1632389320);
        List<TemperatureSensor> temperatureSensors = Collections.singletonList(TemperatureSensor.builder().epochDatetime(instant).temperatureInCelsius(32).build());
        when(temperatureRepository.findByEpochDatetimeGreaterThanEqualAndEpochDatetimeLessThanEqual(Instant.ofEpochSecond(1632389320),Instant.ofEpochSecond(1632389337))).thenReturn(temperatureSensors);
        List<TemperatureSensorResponse> result = reportService.getDataReport(1632389320,1632389337);
        assertEquals(1632389320,result.get(0).getEpochTimestamp());
        assertEquals(32.0,result.get(0).getTemperatureInCelsius(),0.01);
        assertEquals(89.60,result.get(0).getTemperatureInFahrenheit(),0.01);


    }

    @Test
    public void shouldReturnEmptyReportWhenPassedDateRangeNotFound(){
        List<TemperatureSensor> temperatureSensors = Collections.emptyList();
        when(temperatureRepository.findByEpochDatetimeGreaterThanEqualAndEpochDatetimeLessThanEqual(Instant.ofEpochSecond(1632389321),Instant.ofEpochSecond(1632389337))).thenReturn(temperatureSensors);
        List<TemperatureSensorResponse> result = reportService.getDataReport(1632389321,1632389337);
        assertEquals(result.size(),0);
    }
}
