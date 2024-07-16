package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SolarWatchController {
    private final SolarWatchService solarWatchService;

    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/solarwatch")
    public String getSolarWatchReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().toString()}") String date, Model model) {
        SunriseSunset solarWatchRep = solarWatchService.getSolarWatchReport(city, date);
        model.addAttribute("city", city);
        model.addAttribute("date", date);
        model.addAttribute("solarWatchRep", solarWatchRep);
        return "solarWatch";
    }
}
