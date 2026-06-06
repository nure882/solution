package com.gasstation.model.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "station_id", nullable = false, updatable = false)
    private UUID stationId;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "work_time")
    private String workTime;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "equipment_list", length = 1000)
    private String equipmentList;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Station() {}

    public Station(String city, String address, String workTime, String description, String equipmentList, OffsetDateTime createdAt) {
        this.city = city;
        this.address = address;
        this.workTime = workTime;
        this.description = description;
        this.equipmentList = equipmentList;
        this.createdAt = createdAt;
    }

    public UUID getStationId() { return stationId; }
    public void setStationId(UUID stationId) { this.stationId = stationId; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getWorkTime() { return workTime; }
    public void setWorkTime(String workTime) { this.workTime = workTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getEquipmentList() { return equipmentList; }
    public void setEquipmentList(String equipmentList) { this.equipmentList = equipmentList; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
