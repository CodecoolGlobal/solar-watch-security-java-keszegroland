package com.codecool.solarwatch.exception;

public class InvalidCityNameException extends RuntimeException {
    public InvalidCityNameException(String cityName) {
        super(cityName + " is not a valid city name.");
    }
}
