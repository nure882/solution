package com.gasstation.model.dto.station;

import java.time.OffsetDateTime;
import java.util.UUID;

public class StationDto {
    private UUID stationId;
    private String city;
    private String address;
    private String workTime;
    private String description;
    private String equipmentList;
    private OffsetDateTime createdAt;

    public StationDto(UUID stationId, String city, String address, String workTime, String description, String equipmentList, OffsetDateTime createdAt) {
        this.stationId = stationId;
        this.city = city;
        this.address = address;
        this.workTime = workTime;
        this.description = description;
        this.equipmentList = equipmentList;
        this.createdAt = createdAt;
    }

    public UUID getStationId() { return stationId; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getWorkTime() { return workTime; }
    public String getDescription() { return description; }
    public String getEquipmentList() { return equipmentList; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
