package com.codecool.solarwatch.model.payload;

import lombok.Data;

@Data
public class CreateSolarWatchReportRequest {
    private String cityName;
    private double latitude;
    private double longitude;
    private String state;
    private String country;
    private String sunrise;
    private String sunset;
}
