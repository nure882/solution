package com.gasstation.model.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "gas_types")
public class GasType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gas_type_id", nullable = false, updatable = false)
    private UUID gasTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected GasType() {}

    public GasType(String name, OffsetDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getGasTypeId() { return gasTypeId; }
    public void setGasTypeId(UUID gasTypeId) { this.gasTypeId = gasTypeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
