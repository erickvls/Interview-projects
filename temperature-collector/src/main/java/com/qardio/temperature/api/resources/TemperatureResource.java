package com.qardio.temperature.api.resources;

import com.qardio.temperature.api.dto.TemperatureSensorRequest;
import com.qardio.temperature.api.service.TemperatureSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * The class provide API endpoint to collect temperature data from sensors.
 */
@RestController
@RequestMapping(value = "api/v1")
public class TemperatureResource {

    private final TemperatureSensorService temperatureSensorService;

    @Autowired
    public TemperatureResource(TemperatureSensorService temperatureSensorService) {
        this.temperatureSensorService = temperatureSensorService;
    }

    /**
     * Collect temperature that has been sent by the client.
     * @param request A list of temperatures in a bulk, if there is only one, so
     *                it will receive just one data in an array.
     * @return A Response entity indicating request status and HTTP Status 204.It means that the request
     *  was processed, but don't need to return an entity-body.
     *
     */
    @PostMapping(value = "/temperature", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> collectTemperature(@RequestBody List<@Valid TemperatureSensorRequest> request) {
        temperatureSensorService.sendTemperature(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
