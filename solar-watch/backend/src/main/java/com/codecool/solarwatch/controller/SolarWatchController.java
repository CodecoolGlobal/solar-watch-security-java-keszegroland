package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.dto.SolarWatchReportDTO;
import com.codecool.solarwatch.model.payload.SolarWatchReportRequest;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solarwatch")
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping
    public SolarWatchReportDTO getSolarWatchReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String date) {
        return solarWatchService.getSunInformation(city, date);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public SolarWatchReportDTO createSolarWatchReport(@RequestBody SolarWatchReportRequest request) {
        return solarWatchService.createNewSolarWatchReport(request);
    }

    @PatchMapping("/update/{sunId}")
    @PreAuthorize("hasRole('ADMIN')")
    public SolarWatchReportDTO updateSolarWatchReportById(@PathVariable long sunId, @RequestBody SolarWatchReportRequest updatedReport) {
        return solarWatchService.updateSolarWatchReportById(sunId, updatedReport);
    }

    @DeleteMapping("/delete/{cityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Long deleteSolarWatchReportByCityId(@PathVariable long cityId) {
        return solarWatchService.deleteSolarWatchReportByCityId(cityId);
    }

    @GetMapping("/getAllReports")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SolarWatchReportDTO> getAllSolarWatchReports() {
        return solarWatchService.getAllSolarWatchReports();
    }

    @GetMapping("/{sunId}")
    @PreAuthorize("hasRole('ADMIN')")
    public SolarWatchReportDTO getSolarWatchReportById(@PathVariable long sunId) {
        return solarWatchService.getSolarWatchReportById(sunId);
    }
}