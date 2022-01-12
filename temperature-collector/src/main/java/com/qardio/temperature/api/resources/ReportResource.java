package com.qardio.temperature.api.resources;

import com.qardio.temperature.api.dto.TemperatureSensorResponse;
import com.qardio.temperature.api.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The class provide a API endpoint to get report information.
 */

@RestController
@RequestMapping(value = "api/v1")
public class ReportResource {

    private final ReportService reportService;

    public ReportResource(ReportService reportService){
        this.reportService = reportService;
    }

    /**
     * It receives a range of datetime to generate a report.
     * @param startTime The star time  in epoch timestamp.
     * @param endTime the end time in epoch timestamp.
     * @return A data list containing all temperatures with its time.
     */
    @GetMapping(value = "/report/temperatures")
    public ResponseEntity<List<TemperatureSensorResponse>> generateReport(@RequestParam long startTime, @RequestParam long endTime) {
        return new ResponseEntity<>(reportService.getDataReport(startTime,endTime),HttpStatus.OK);
    }
}
