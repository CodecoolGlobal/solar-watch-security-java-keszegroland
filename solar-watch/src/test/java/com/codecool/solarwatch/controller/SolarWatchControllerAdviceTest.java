/*
package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolarWatchControllerAdviceTest {

    private SolarWatchControllerAdvice solarWatchControllerAdvice;

    @BeforeEach
    void setUp() {
        solarWatchControllerAdvice = new SolarWatchControllerAdvice();
    }

    @Test
    void handleInvalidCityNameException_ShouldReturnInvalidCityNameExceptionMessage() {
        InvalidCityNameException exception = new InvalidCityNameException();
        String actual = solarWatchControllerAdvice.handleInvalidCityNameException(exception);
        String expected = "Invalid city name.";
        assertEquals(expected, actual);
    }

    @Test
    void handleInvalidDateException_ShouldReturnInvalidDateExceptionMessage() {
        InvalidDateException exception = new InvalidDateException();
        String actual = solarWatchControllerAdvice.handleInvalidDateException(exception);
        String expected = "Date must be a valid date. (format: yyyy-MM-dd)";
        assertEquals(expected, actual);
    }
}*/
