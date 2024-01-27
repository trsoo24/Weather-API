package com.weather.api.repository;

import com.weather.api.model.entity.UltShortTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UltShortTermRepository extends JpaRepository<UltShortTerm, Long> {
    Optional<UltShortTerm> findByDateTime(String dateTime);
}
