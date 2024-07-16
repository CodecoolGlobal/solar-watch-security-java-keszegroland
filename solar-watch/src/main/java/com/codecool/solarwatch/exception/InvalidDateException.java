package com.codecool.solarwatch.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Date must be a valid date. (format: yyyy-MM-dd)");
    }
}
