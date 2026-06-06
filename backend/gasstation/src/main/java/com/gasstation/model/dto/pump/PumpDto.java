package com.gasstation.model.dto.pump;

import java.util.UUID;

public class PumpDto {
    private UUID pumpId;
    private int number;
    private double gasCount;
    private double cost;
    private UUID stationId;
    private UUID gasTypeId;
    private String gasTypeName;

    public PumpDto(UUID pumpId, int number, double gasCount, double cost, UUID stationId, UUID gasTypeId, String gasTypeName) {
        this.pumpId = pumpId;
        this.number = number;
        this.gasCount = gasCount;
        this.cost = cost;
        this.stationId = stationId;
        this.gasTypeId = gasTypeId;
        this.gasTypeName = gasTypeName;
    }

    public UUID getPumpId() { return pumpId; }
    public int getNumber() { return number; }
    public double getGasCount() { return gasCount; }
    public double getCost() { return cost; }
    public UUID getStationId() { return stationId; }
    public UUID getGasTypeId() { return gasTypeId; }
    public String getGasTypeName() { return gasTypeName; }
}
