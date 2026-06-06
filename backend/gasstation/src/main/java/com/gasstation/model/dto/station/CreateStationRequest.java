package com.gasstation.model.dto.station;

public class CreateStationRequest {
    private String city;
    private String address;
    private String workTime;
    private String description;
    private String equipmentList;

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
}
