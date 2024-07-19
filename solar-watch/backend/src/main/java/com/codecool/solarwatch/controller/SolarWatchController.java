package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.CreateSolarWatchReportRequest;
import com.codecool.solarwatch.model.payload.SolarWatchReportRequest;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solarwatch")
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping
    public ResponseEntity<?> getSolarWatchReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String date) {
        return ResponseEntity.ok(solarWatchService.getSunInformation(city, date));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createSolarWatchReport(@RequestBody CreateSolarWatchReportRequest request) {
        solarWatchService.createNewSolarWatchReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update/{sunId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateSolarWatchReport(@PathVariable long sunId, @RequestBody SolarWatchReportRequest updatedReport) {
        solarWatchService.updateSolarWatchReportById(sunId, updatedReport);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{cityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSolarWatchReportByCityId(@PathVariable long cityId) {
        solarWatchService.deleteSolarWatchReportByCityId(cityId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}