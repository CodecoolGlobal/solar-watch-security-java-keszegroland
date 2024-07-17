package com.codecool.solarwatch.service;

import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunRepository;
import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class SolarWatchService {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String GEO_API_URL = "https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s";
    private static final String SUN_API_URL = "https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s";
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);
    private final CityRepository cityRepository;
    private final SunRepository sunRepository;

    @Autowired
    public SolarWatchService(RestTemplate restTemplate, CityRepository cityRepository, SunRepository sunRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
        this.sunRepository = sunRepository;
    }

    private CityEntity getCityByName(String cityName) {
        Optional<CityEntity> city = cityRepository.findByName(cityName);
        return city.orElseGet(() -> fetchCityByNameFromAPI(cityName));
    }

    private CityEntity fetchCityByNameFromAPI(String cityName) {
        String url = String.format(GEO_API_URL, cityName, API_KEY);
        LongitudeLatitude[] response = restTemplate.getForObject(url, LongitudeLatitude[].class);
        logger.info("Response from Open Weather API: {}", (Object) response);
        if (response == null || response.length == 0) {
            throw new InvalidCityNameException(cityName);
        }

        LongitudeLatitude report = response[0];
        return cityRepository.findByName(report.name()).orElseGet(() -> saveNewCity(report));
    }

    private CityEntity saveNewCity(LongitudeLatitude report) {
        CityEntity newCity = new CityEntity();
        newCity.setName(report.name());
        newCity.setLat(report.lat());
        newCity.setLon(report.lon());
        newCity.setState(report.state());
        newCity.setCountry(report.country());
        cityRepository.save(newCity);
        return newCity;
    }

    private LocalDate convertDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }

    public SunEntity getSunInformation(String cityName, String date) {
        CityEntity city = getCityByName(cityName);
        LocalDate searchDate = convertDate(date);
        Optional<SunEntity> sun = sunRepository.findByCityAndDate(city, searchDate);
        return sun.orElseGet(() -> fetchSunFromAPI(city, searchDate));
    }

    private SunEntity fetchSunFromAPI(CityEntity city, LocalDate searchDate) {
        String url = String.format(SUN_API_URL, city.getLat(), city.getLon(), searchDate);
        SunriseSunsetResult response = restTemplate.getForObject(url, SunriseSunsetResult.class);
        logger.info("Response from Sunrise-Sunset API: {}", response);
        if (response == null) {
            throw new RuntimeException("Failed to fetch sun information for city: " + city.getName() + " on date: " + searchDate);
        }
        return saveNewSun(response.results(), city, searchDate);
    }

    private SunEntity saveNewSun(SunriseSunset sunResult, CityEntity city, LocalDate searchDate) {
        SunEntity newSun = new SunEntity();
        newSun.setSunrise(sunResult.sunrise());
        newSun.setSunset(sunResult.sunset());
        newSun.setCity(city);
        newSun.setDate(searchDate);
        sunRepository.save(newSun);
        return newSun;
    }
}