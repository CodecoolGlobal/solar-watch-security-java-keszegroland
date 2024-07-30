package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.dto.SolarWatchReportDTO;
import com.codecool.solarwatch.model.payload.SolarWatchReportRequest;
import com.codecool.solarwatch.service.SolarWatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolarWatchController.class)
@TestPropertySource(locations = "classpath:application-integrationTest.properties")
class SolarWatchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SolarWatchService solarWatchServiceMock;

    private SolarWatchReportDTO solarWatchReportDTOMock;
    private String city;
    private String date;
    private double latitude;
    private double longitude;


    @BeforeEach
    void setUp() {
        city = "Budapest";
        date = "2024-07-30";
        latitude = 47.4979;
        longitude = 19.0402;

        solarWatchReportDTOMock = new SolarWatchReportDTO(1L, city, latitude, longitude, 1L, "", "Hungary", "05:00:00", "20:00:00");
    }

    @Test
    @WithMockUser
    public void givenCityNameAndDate_WhenGetSolarWatchReport_ThenStatus200() throws Exception {
        when(solarWatchServiceMock.getSunInformation(city, date)).thenReturn(solarWatchReportDTOMock);

        mockMvc.perform(get("/api/solarwatch")
                        .param("city", city)
                        .param("date", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cityName")
                        .value(city))
                .andExpect(jsonPath("$.sunrise")
                        .value("05:00:00"))
                .andExpect(jsonPath("$.sunset")
                        .value("20:00:00"));

        verify(solarWatchServiceMock).getSunInformation(city, date);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenSolarWatchReportRequest_WhenCreateSolarWatchReport_ThenStatus201() throws Exception {
        SolarWatchReportRequest reportRequestMock = new SolarWatchReportRequest();
        reportRequestMock.setCityName(city);
        reportRequestMock.setLatitude(latitude);
        reportRequestMock.setLongitude(longitude);
        reportRequestMock.setState("");
        reportRequestMock.setCountry("Hungary");
        reportRequestMock.setSunrise("05:00:00");
        reportRequestMock.setSunset("20:00:00");

        String requestJson = objectMapper.writeValueAsString(reportRequestMock);

        when(solarWatchServiceMock.createNewSolarWatchReport(reportRequestMock)).thenReturn(solarWatchReportDTOMock);
        mockMvc.perform(post("/api/solarwatch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cityName")
                        .value(city))
                .andExpect(jsonPath("$.latitude")
                        .value(latitude))
                .andExpect(jsonPath("$.longitude")
                        .value(longitude))
                .andExpect(jsonPath("$.sunrise")
                        .value("05:00:00"))
                .andExpect(jsonPath("$.sunset")
                        .value("20:00:00"));

        verify(solarWatchServiceMock).createNewSolarWatchReport(reportRequestMock);
    }
}
