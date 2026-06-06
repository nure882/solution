package com.gasstation.model.dto.pump;

import java.util.UUID;

public class CreatePumpRequest {
    private int number;
    private double gasCount;
    private double cost;
    private UUID stationId;
    private UUID gasTypeId;

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public double getGasCount() { return gasCount; }
    public void setGasCount(double gasCount) { this.gasCount = gasCount; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public UUID getStationId() { return stationId; }
    public void setStationId(UUID stationId) { this.stationId = stationId; }
    public UUID getGasTypeId() { return gasTypeId; }
    public void setGasTypeId(UUID gasTypeId) { this.gasTypeId = gasTypeId; }
}
