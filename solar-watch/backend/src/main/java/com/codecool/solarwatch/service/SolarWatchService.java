package com.codecool.solarwatch.service;

import com.codecool.solarwatch.dto.*;
import com.codecool.solarwatch.model.payload.SolarWatchReportRequest;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunRepository;
import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class SolarWatchService {
    @Value("${api.key}")
    private String api_key;

    @Value("${geo.api.url}")
    private String geo_api_url;

    @Value("${sun.api.url}")
    private String sun_api_url;

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
        return cityRepository.findByName(cityName).orElseGet(() -> fetchCityByNameFromAPI(cityName));
    }

    private CityEntity fetchCityByNameFromAPI(String cityName) {
        String url = String.format(geo_api_url, cityName, api_key);
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
        setCityFields(newCity, report);
        cityRepository.save(newCity);
        return newCity;
    }

    private void setCityFields(CityEntity city, LongitudeLatitude report) {
        city.setName(report.name());
        city.setLat(report.lat());
        city.setLon(report.lon());
        city.setState(report.state());
        city.setCountry(report.country());
    }

    private LocalDate convertDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
    }

    public SolarWatchReportDTO getSunInformation(String cityName, String date) {
        CityEntity city = getCityByName(cityName);
        LocalDate searchDate = convertDate(date);
        SunEntity sunEntity = sunRepository.findByCityAndDate(city, searchDate).orElseGet(() -> fetchSunFromAPI(city, searchDate));
        return convertSunEntityToSolarWatchReportDTO(sunEntity);
    }

    private SunEntity fetchSunFromAPI(CityEntity city, LocalDate searchDate) {
        String url = String.format(sun_api_url, city.getLat(), city.getLon(), searchDate);
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

    public SolarWatchReportDTO createNewSolarWatchReport(SolarWatchReportRequest request) {
        CityEntity cityEntity = cityRepository.findByName(request.getCityName()).orElseGet(() -> createNewCityEntity(request));
        SunriseSunset sunriseSunset = new SunriseSunset(request.getSunrise(), request.getSunset());
        SunEntity sunEntity = saveNewSun(sunriseSunset, cityEntity, LocalDate.now());

        return convertSunEntityToSolarWatchReportDTO(sunEntity);
    }

    private CityEntity createNewCityEntity(SolarWatchReportRequest request) {
        CityEntity newCity = new CityEntity();
        newCity.setName(request.getCityName());
        newCity.setLat(request.getLatitude());
        newCity.setLon(request.getLongitude());
        newCity.setState(request.getState());
        newCity.setCountry(request.getCountry());
        return cityRepository.save(newCity);
    }

    public SolarWatchReportDTO updateSolarWatchReportById(long sunId, SolarWatchReportRequest updatedReport) {
        SunEntity sunEntity = sunRepository.findById(sunId).orElseThrow(() -> new RuntimeException("Sun with this id does not exist."));
        CityEntity cityEntity = sunEntity.getCity();
        updateCityEntity(cityEntity, updatedReport);
        updateSunEntity(sunEntity, updatedReport, cityEntity);

        return convertSunEntityToSolarWatchReportDTO(sunEntity);
    }

    private void updateCityEntity(CityEntity cityEntity, SolarWatchReportRequest updatedReport) {
        cityEntity.setCountry(updatedReport.getCountry());
        cityEntity.setName(updatedReport.getCityName());
        cityEntity.setLat(updatedReport.getLatitude());
        cityEntity.setLon(updatedReport.getLongitude());
        cityEntity.setState(updatedReport.getState());
        cityEntity.setCountry(updatedReport.getCountry());
        cityRepository.save(cityEntity);
    }

    private void updateSunEntity(SunEntity sunEntity, SolarWatchReportRequest updatedReport, CityEntity cityEntity) {
        sunEntity.setSunrise(updatedReport.getSunrise());
        sunEntity.setSunset(updatedReport.getSunset());
        sunEntity.setCity(cityEntity);
        sunEntity.setDate(LocalDate.now());
        sunRepository.save(sunEntity);
    }

    public Long deleteSolarWatchReportByCityId(long cityId) {
        cityRepository.deleteById(cityId);
        return cityId;
    }

    public List<SolarWatchReportDTO> getAllSolarWatchReports() {
        List<SunEntity> sunEntities = sunRepository.findAll();
        return sunEntities.stream().map(this::convertSunEntityToSolarWatchReportDTO).toList();
    }

    private SolarWatchReportDTO convertSunEntityToSolarWatchReportDTO(SunEntity sunEntity) {
        CityEntity cityEntity = sunEntity.getCity();
        return new SolarWatchReportDTO(sunEntity.getId(), cityEntity.getName(), cityEntity.getLat(), cityEntity.getLon(), cityEntity.getId(), cityEntity.getState(), cityEntity.getCountry(), sunEntity.getSunrise(), sunEntity.getSunset());
    }

    public SolarWatchReportDTO getSolarWatchReportById(long sunId) {
        SunEntity sunEntity = sunRepository.findById(sunId).orElseThrow(() -> new RuntimeException("Sun with this id does not exist."));
        return convertSunEntityToSolarWatchReportDTO(sunEntity);
    }
}