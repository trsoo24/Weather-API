package com.weather.api.repository;

import com.weather.api.model.entity.ShortTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortTermRepository extends JpaRepository<ShortTerm, Long> {

}
