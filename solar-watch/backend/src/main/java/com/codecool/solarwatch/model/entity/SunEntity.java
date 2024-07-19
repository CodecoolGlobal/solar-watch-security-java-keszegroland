package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "suns")
public class SunEntity {
    @Id
    @GeneratedValue
    private long id;
    private String sunrise;
    private String sunset;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
    private LocalDate date;

}
