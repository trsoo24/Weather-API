package com.weather.api.repository;

import com.weather.api.model.entity.MidTermTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidTermTotalRepository extends JpaRepository<MidTermTotal, Long> {
}
