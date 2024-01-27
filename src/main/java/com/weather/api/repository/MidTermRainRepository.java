package com.weather.api.repository;

import com.weather.api.model.entity.MidTermRain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MidTermRainRepository extends JpaRepository<MidTermRain, Long> {
    Optional<MidTermRain> findByDateAndLocation(String date, String location);
}
