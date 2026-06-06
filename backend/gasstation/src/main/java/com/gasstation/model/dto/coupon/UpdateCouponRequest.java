package com.gasstation.model.dto.coupon;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UpdateCouponRequest {
    private UUID gasTypeId;
    private double liters;
    private Boolean active;
    private OffsetDateTime expirationDate;
    private OffsetDateTime useDate;

    public UUID getGasTypeId() { return gasTypeId; }
    public void setGasTypeId(UUID gasTypeId) { this.gasTypeId = gasTypeId; }
    public double getLiters() { return liters; }
    public void setLiters(double liters) { this.liters = liters; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public OffsetDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(OffsetDateTime expirationDate) { this.expirationDate = expirationDate; }
    public OffsetDateTime getUseDate() { return useDate; }
    public void setUseDate(OffsetDateTime useDate) { this.useDate = useDate; }
}
