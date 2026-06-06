package com.gasstation.repository;

import com.gasstation.model.entity.GasType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GasTypeRepository extends JpaRepository<GasType, UUID> {
    List<GasType> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}
