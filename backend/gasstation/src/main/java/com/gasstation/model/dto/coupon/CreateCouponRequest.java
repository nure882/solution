package com.gasstation.model.dto.coupon;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateCouponRequest {
    private UUID customerId;
    private UUID gasTypeId;
    private double liters;
    private OffsetDateTime expirationDate;

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public UUID getGasTypeId() { return gasTypeId; }
    public void setGasTypeId(UUID gasTypeId) { this.gasTypeId = gasTypeId; }
    public double getLiters() { return liters; }
    public void setLiters(double liters) { this.liters = liters; }
    public OffsetDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(OffsetDateTime expirationDate) { this.expirationDate = expirationDate; }
}
