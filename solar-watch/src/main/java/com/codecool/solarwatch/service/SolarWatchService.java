package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.LongitudeLatitude;
import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.model.SunriseSunsetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Service
public class SolarWatchService {
    private static final String API_KEY = System.getenv("API_KEY");
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);

    @Autowired
    public SolarWatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private LongitudeLatitude getOpenWeatherReport(String city) {
        String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", city, API_KEY);

        LongitudeLatitude[] result = restTemplate.getForObject(url, LongitudeLatitude[].class);
        if (result != null && result.length > 0) {
            logger.info("lat and lon: {}, {}", result[0].lat(), result[0].lon());
            return new LongitudeLatitude(result[0].lat(), result[0].lon());
        } else {
            logger.info("Error city name is not found.");
            throw new InvalidCityNameException();
        }
    }

    private LocalDate convertDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }

    public SunriseSunset getSolarWatchReport(String city, String date) {
        LongitudeLatitude openWeatherResponse = getOpenWeatherReport(city);
        LocalDate convertedDate = convertDate(date);
        logger.info("lat and long and date: {}, {}, {}", openWeatherResponse.lat(), openWeatherResponse.lon(), convertedDate);

        String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", openWeatherResponse.lat(), openWeatherResponse.lon(), convertedDate);
        SunriseSunsetResult response = restTemplate.getForObject(url, SunriseSunsetResult.class);

        return new SunriseSunset(Objects.requireNonNull(response).results().sunrise(), response.results().sunset());
    }

}
