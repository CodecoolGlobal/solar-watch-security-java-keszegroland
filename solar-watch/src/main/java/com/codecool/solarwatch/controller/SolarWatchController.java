package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/async/solarwatch")
    public Mono<SunriseSunset> getSolarWatchReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String date) {
        return solarWatchService.getSolarWatchReport(city, date);
    }

}