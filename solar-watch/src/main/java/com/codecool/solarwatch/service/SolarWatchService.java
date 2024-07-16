package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class SolarWatchService {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String GEO_API_URL = "https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";
    private static final String SUN_API_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s";
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);

    @Autowired
    public SolarWatchService(WebClient webClient) {
        this.webClient = webClient;
    }

    private Mono<LongitudeLatitude> getOpenWeatherReport(String city) {
        String url = String.format(GEO_API_URL, city, API_KEY);

        Mono<LongitudeLatitude[]> result = getOpenWeatherReportResult(url);

        return result.flatMap(response -> {
            if (response != null && response.length > 0) {
                logger.info("lat and lon: {}, {}", response[0].lat(), response[0].lon());
                return Mono.just(new LongitudeLatitude(response[0].lat(), response[0].lon()));
            } else {
                logger.info("Error city name is not found.");
                return Mono.error(new InvalidCityNameException());
            }
        });
    }

    private Mono<LongitudeLatitude[]> getOpenWeatherReportResult(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(LongitudeLatitude[].class)
                .log();
    }

    private LocalDate convertDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }

    public Mono<SunriseSunset> getSolarWatchReport(String city, String date) {
        Mono<LongitudeLatitude> openWeatherResponse = getOpenWeatherReport(city);
        LocalDate convertedDate = convertDate(date);

        return openWeatherResponse.flatMap(openWeatherResult -> {
            String url = String.format(SUN_API_URL, openWeatherResult.lat(), openWeatherResult.lon(), convertedDate);
            Mono<SunriseSunsetResult> result = getSolarWatchReportResult(url);

            return result.map(response -> new SunriseSunset(response.results().sunrise(), response.results().sunset()));
        });
    }

    private Mono<SunriseSunsetResult> getSolarWatchReportResult(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(SunriseSunsetResult.class)
                .log();
    }

}