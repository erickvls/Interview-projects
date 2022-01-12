package com.qardio.temperature.api.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qardio.temperature.api.TemperatureCollectorApplication;
import com.qardio.temperature.api.dto.TemperatureSensorResponse;
import com.qardio.temperature.api.service.ReportService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TemperatureCollectorApplication.class})
public class ReportResourceIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnReportWhenPassDates() throws Exception {

        TemperatureSensorResponse data = TemperatureSensorResponse.builder()
                .temperatureInCelsius(24.3)
                .epochTimestamp(1632381171)
                .temperatureInFahrenheit(75.74)
                .build();
        List<TemperatureSensorResponse> result = Collections.singletonList(data);

        when(reportService.getDataReport(anyLong(),anyLong())).thenReturn(result);

        mockMvc.perform(get("/api/v1/report/temperatures")
                .param("startTime", "1563142796")
                .param("endTime", "1563142800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].temperatureInCelsius").value("24.3"))
                .andExpect(jsonPath("$.[0].temperatureInFahrenheit").value("75.74"))
                .andExpect(jsonPath("$.[0].epochTimestamp").value("1632381171"))
                .andDo(print());

    }

    @Test
    public void shouldReturnMethodNotAllowedWhenIsNotGet() throws Exception {

        TemperatureSensorResponse data = TemperatureSensorResponse.builder()
                .temperatureInCelsius(24.3)
                .epochTimestamp(1632381171)
                .temperatureInFahrenheit(75.74)
                .build();
        List<TemperatureSensorResponse> result = Collections.singletonList(data);

        when(reportService.getDataReport(anyLong(),anyLong())).thenReturn(result);

        mockMvc.perform(post("/api/v1/report/temperatures")
                .param("startTime", "1563142796")
                .param("endTime", "1563142800"))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());

    }


}
