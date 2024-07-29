package com.codecool.solarwatch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LongitudeLatitude(String name, double lat, double lon, String state, String country) {
}