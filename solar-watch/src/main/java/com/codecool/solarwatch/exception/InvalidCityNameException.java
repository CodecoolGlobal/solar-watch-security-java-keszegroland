package com.codecool.solarwatch.exception;

public class InvalidCityNameException extends RuntimeException {
    public InvalidCityNameException() {
        super("Invalid city name.");
    }
}
