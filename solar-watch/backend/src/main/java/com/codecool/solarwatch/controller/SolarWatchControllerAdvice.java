package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidCityNameException;
import com.codecool.solarwatch.exception.InvalidDateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SolarWatchControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidCityNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidCityNameException(InvalidCityNameException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidDateException(InvalidDateException ex) {
        return ex.getMessage();
    }
}
