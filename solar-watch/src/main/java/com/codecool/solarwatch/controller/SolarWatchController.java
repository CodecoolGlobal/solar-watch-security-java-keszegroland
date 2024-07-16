package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/solarwatch")
    public ResponseEntity<?> getSolarWatchReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String date) {
        return ResponseEntity.ok(solarWatchService.getSolarWatchReport(city, date));
    }

}
