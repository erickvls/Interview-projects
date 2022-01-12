package com.qardio.temperature.api.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qardio.temperature.api.TemperatureCollectorApplication;
import com.qardio.temperature.api.dto.TemperatureSensorRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TemperatureCollectorApplication.class})
public class TemperatureResourceIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnNoContentWhenRequestIsCorrect() throws Exception {
        List<TemperatureSensorRequest> responses = new ArrayList<>();
        TemperatureSensorRequest data = TemperatureSensorRequest.builder()
                .temperatureInCelsius(24.3)
                .epochTimestamp(1632381171)
                .build();
        responses.add(data);
        mockMvc.perform(post("/api/v1/temperature")
                .content(objectMapper.writeValueAsString((responses)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void shouldReturnUnsupportedMediaTypeWhenContentTypeIsNotJson() throws Exception {
        List<TemperatureSensorRequest> responses = new ArrayList<>();
        TemperatureSensorRequest data = TemperatureSensorRequest.builder()
                .temperatureInCelsius(24.3)
                .epochTimestamp(1632381171)
                .build();
        responses.add(data);
        mockMvc.perform(post("/api/v1/temperature")
                .content(objectMapper.writeValueAsString((responses)))
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isUnsupportedMediaType())
                .andDo(print());
    }


    @Test
    public void shouldReturnMethodNotAllowedWhenIsNotPost() throws Exception {
        List<TemperatureSensorRequest> responses = new ArrayList<>();
        TemperatureSensorRequest data = TemperatureSensorRequest.builder()
                .temperatureInCelsius(24.3)
                .epochTimestamp(1632381171)
                .build();
        responses.add(data);
        mockMvc.perform(get("/api/v1/temperature")
                .content(objectMapper.writeValueAsString((responses)))
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }
}
