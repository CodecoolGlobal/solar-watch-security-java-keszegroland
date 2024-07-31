package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.dto.SolarWatchReportDTO;
import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.service.SolarWatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolarWatchControllerTest {
    private SolarWatchController solarWatchController;
    private SolarWatchService solarWatchServiceMock;

    @BeforeEach
    void setUp() {
        solarWatchServiceMock = mock(SolarWatchService.class);
        solarWatchController = new SolarWatchController(solarWatchServiceMock);
    }

    @Test
    void getSolarWatchReport_WhenTheSelectedCityIsBudapest_AndTheSelectedDateIs20240604_SuccessfullyTest() {
        SolarWatchReportDTO solarWatchReportDTOExpected = new SolarWatchReportDTO(1L, "Budapest", 47.4979, 19.0402, 1L, "", "Hungary", "05:00:00", "20:00:00");
        when(solarWatchServiceMock.getSunInformation("Budapest", "2024-06-04")).thenReturn(solarWatchReportDTOExpected);

        SolarWatchReportDTO actual = solarWatchController.getSolarWatchReport("Budapest", "2024-06-04");
        assertEquals(solarWatchReportDTOExpected, actual);
        verify(solarWatchServiceMock).getSunInformation("Budapest", "2024-06-04");
    }

    @Test
    void getSolarWatchReport_WhenTheSelectedCityIsInvalid_AndTheSelectedDateIs20240604_ThrowsCustomInvalidCityNameException() {
        when(solarWatchServiceMock.getSunInformation("InvalidCityName", "2024-06-04")).thenThrow(InvalidCityNameException.class);
        assertThrows(InvalidCityNameException.class, () -> solarWatchController.getSolarWatchReport("InvalidCityName", "2024-06-04"));
        verify(solarWatchServiceMock).getSunInformation("InvalidCityName", "2024-06-04");
    }


}
