package com.codecool.solarwatch.service;

import com.codecool.solarwatch.dto.SolarWatchReportDTO;
import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunEntity;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolarWatchServiceTest {

    private CityRepository cityRepositoryMock;

    private SunRepository sunRepositoryMock;

    private SolarWatchService solarWatchService;

    private SunEntity sunEntity;

    private CityEntity cityEntity;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        cityRepositoryMock = mock(CityRepository.class);
        sunRepositoryMock = mock(SunRepository.class);

        cityEntity = new CityEntity();
        cityEntity.setName("Budapest");
        cityEntity.setLat(47.4979);
        cityEntity.setLon(19.0402);
        cityEntity.setState("");
        cityEntity.setCountry("Hungary");

        sunEntity = new SunEntity();
        sunEntity.setSunrise("05:00:00");
        sunEntity.setSunset("20:00:00");
        sunEntity.setCity(cityEntity);
        sunEntity.setDate(LocalDate.parse("2024-06-04"));

        solarWatchService = new SolarWatchService(restTemplateMock, cityRepositoryMock, sunRepositoryMock);
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIs20240604_SuccessfullyTest() {
        String cityName = "Budapest";
        LocalDate localDate = LocalDate.parse("2024-06-04");

        when(cityRepositoryMock.findByName(cityName)).thenReturn(Optional.of(cityEntity));
        when(sunRepositoryMock.findByCityAndDate(cityEntity, localDate)).thenReturn(Optional.of(sunEntity));

        SolarWatchReportDTO expected = new SolarWatchReportDTO(sunEntity.getId(), cityName, cityEntity.getLat(), cityEntity.getLon(), cityEntity.getId(), cityEntity.getState(), cityEntity.getCountry(), sunEntity.getSunrise(), sunEntity.getSunset());

        SolarWatchReportDTO actual = solarWatchService.getSunInformation(cityName, "2024-06-04");

        assertEquals(actual, expected);
        verify(cityRepositoryMock).findByName(cityName);
        verify(sunRepositoryMock).findByCityAndDate(cityEntity, localDate);
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIsInvalid_ThrowsCustomInvalidDateException() {
        when(sunRepositoryMock.findByCityAndDate(any(), any())).thenThrow(new InvalidDateException());

        assertThrows(InvalidDateException.class, () -> {
            solarWatchService.getSunInformation("Budapest", "invalidDate");
        });

        verify(sunRepositoryMock, never()).findByCityAndDate(any(), any());
    }

    @Test
    void getSunriseAndSunsetTime_WhenTheSelectedCityIsInvalid_AndTheSelectedDateIs20240604_ThrowsCustomInvalidCityNameException() {
        when(cityRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());

        ReflectionTestUtils.setField(solarWatchService, "geo_api_url", "https://mockURL.com");
        ReflectionTestUtils.setField(solarWatchService, "api_key", "mock_api_key");

        assertThrows(InvalidCityNameException.class, () -> {
            solarWatchService.getSunInformation("invalidCityName", "2024-06-04");
        });

        verify(cityRepositoryMock).findByName("invalidCityName");
    }
}
