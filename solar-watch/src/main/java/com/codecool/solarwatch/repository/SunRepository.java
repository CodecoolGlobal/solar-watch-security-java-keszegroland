package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.CityEntity;
import com.codecool.solarwatch.model.entity.SunEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SunRepository extends JpaRepository<SunEntity, Integer> {
    Optional<SunEntity> findByCityAndDate(CityEntity city, LocalDate date);
}
