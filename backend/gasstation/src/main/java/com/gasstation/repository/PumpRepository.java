package com.gasstation.repository;

import com.gasstation.model.entity.Pump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PumpRepository extends JpaRepository<Pump, UUID> {
    List<Pump> findByStation_StationId(UUID stationId);
}
