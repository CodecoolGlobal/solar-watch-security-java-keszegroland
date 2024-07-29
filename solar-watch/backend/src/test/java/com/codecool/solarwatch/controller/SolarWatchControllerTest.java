/*
package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.controller.dto.SunriseSunset;
import com.codecool.solarwatch.service.SolarWatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SolarWatchControllerTest {
    private SolarWatchController solarWatchController;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        SolarWatchService solarWatchService = new SolarWatchService(restTemplate);
        solarWatchController = new SolarWatchController(solarWatchService);
    }

    @Test
    void getSolarWatchReport_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIs20240604_SuccessfullyTest() {
        ResponseEntity<?> actual = solarWatchController.getSolarWatchReport("Budapest", "2024-06-04");
        SunriseSunset expected = new SunriseSunset("2:46:44 AM", "6:37:47 PM");
        assertEquals(expected, actual.getBody());
    }

    @Test
    void getSolarWatchReport_WhenTheSelectedCityIsInvalid_AndTheSelectedDateIs20240604_ThrowsCustomInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> solarWatchController.getSolarWatchReport("InvalidCityName", "2024-06-04"));
    }


}*/
