package com.weather.api.repository;

import com.weather.api.model.entity.ShortTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortTermRepository extends JpaRepository<ShortTerm, Long> {
    Optional<ShortTerm> findByDateTime(String dateTime);
}
