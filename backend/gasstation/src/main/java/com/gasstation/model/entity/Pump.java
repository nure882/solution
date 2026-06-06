package com.gasstation.model.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "pumps")
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pump_id", nullable = false, updatable = false)
    private UUID pumpId;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "gas_count", nullable = false)
    private double gasCount;

    @Column(name = "cost", nullable = false)
    private double cost;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gas_type_id")
    private GasType gasType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Pump() {}

    public Pump(int number, double gasCount, double cost, Station station, GasType gasType, OffsetDateTime createdAt) {
        this.number = number;
        this.gasCount = gasCount;
        this.cost = cost;
        this.station = station;
        this.gasType = gasType;
        this.createdAt = createdAt;
    }

    public UUID getPumpId() { return pumpId; }
    public void setPumpId(UUID pumpId) { this.pumpId = pumpId; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public double getGasCount() { return gasCount; }
    public void setGasCount(double gasCount) { this.gasCount = gasCount; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public Station getStation() { return station; }
    public void setStation(Station station) { this.station = station; }
    public GasType getGasType() { return gasType; }
    public void setGasType(GasType gasType) { this.gasType = gasType; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
