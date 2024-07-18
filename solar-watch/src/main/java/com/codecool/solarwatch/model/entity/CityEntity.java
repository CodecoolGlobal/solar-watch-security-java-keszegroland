package com.codecool.solarwatch.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @OneToMany(mappedBy = "city", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SunEntity> suns;
}
