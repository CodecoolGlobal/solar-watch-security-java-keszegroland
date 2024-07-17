package com.codecool.solarwatch.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Table(name = "cities")
public class CityEntity {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private double lat;
    private double lon;
    private String state;
    private String country;

}
