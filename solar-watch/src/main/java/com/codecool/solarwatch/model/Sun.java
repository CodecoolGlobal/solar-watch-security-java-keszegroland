/*
package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sun {
    @Id
    @GeneratedValue
    private long id;
    private String sunrise;
    private String sunset;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private LocalDate date;

    public long getId() {
        return id;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public City getCity() {
        return city;
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonProperty("sunrise")
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    @JsonProperty("sunset")
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
*/
