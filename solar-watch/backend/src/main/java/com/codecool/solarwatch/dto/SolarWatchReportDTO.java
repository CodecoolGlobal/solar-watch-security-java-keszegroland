package com.codecool.solarwatch.dto;

public record SolarWatchReportDTO(Long sunId, String cityName, double latitude, double longitude, Long cityId, String state,
                                  String country, String sunrise, String sunset) {
}
