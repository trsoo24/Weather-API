package com.weather.api.repository;

import com.weather.api.model.entity.MidTermTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidTermTempRepository extends JpaRepository<MidTermTemp, Long> {
}
