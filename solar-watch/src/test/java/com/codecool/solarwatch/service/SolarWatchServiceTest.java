package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.SunriseSunset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SolarWatchServiceTest {
    private SolarWatchService solarWatchService;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        solarWatchService = new SolarWatchService(restTemplate);
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIs20240604_SuccessfullyTest() {
        SunriseSunset actual = solarWatchService.getSolarWatchReport("Budapest", "2024-06-04");
        SunriseSunset expected = new SunriseSunset("2:46:44 AM", "6:37:47 PM");
        assertEquals(expected, actual);
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIsInvalid_ThrowsCustomInvalidDateException() {
        assertThrows(InvalidDateException.class, () -> {
            solarWatchService.getSolarWatchReport("Budapest", "2024-66-84");
        });
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsInvalid_AndTheSelectedDateIs20240604_ThrowsCustomInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> {
            solarWatchService.getSolarWatchReport("invalidCityName", "2024-06-04");
        });
    }
}