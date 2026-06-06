package com.gasstation.model.dto.coupon;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CouponDto {
    private UUID couponId;
    private UUID customerId;
    private String customerEmail;
    private UUID gasTypeId;
    private String gasTypeName;
    private double liters;
    private boolean active;
    private OffsetDateTime expirationDate;
    private OffsetDateTime useDate;

    public CouponDto(UUID couponId, UUID customerId, String customerEmail, UUID gasTypeId, String gasTypeName,
                     double liters, boolean active, OffsetDateTime expirationDate, OffsetDateTime useDate) {
        this.couponId = couponId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.gasTypeId = gasTypeId;
        this.gasTypeName = gasTypeName;
        this.liters = liters;
        this.active = active;
        this.expirationDate = expirationDate;
        this.useDate = useDate;
    }

    public UUID getCouponId() { return couponId; }
    public UUID getCustomerId() { return customerId; }
    public String getCustomerEmail() { return customerEmail; }
    public UUID getGasTypeId() { return gasTypeId; }
    public String getGasTypeName() { return gasTypeName; }
    public double getLiters() { return liters; }
    public boolean isActive() { return active; }
    public OffsetDateTime getExpirationDate() { return expirationDate; }
    public OffsetDateTime getUseDate() { return useDate; }
}
