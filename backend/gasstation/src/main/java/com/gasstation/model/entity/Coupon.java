package com.gasstation.model.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "coupon_id", nullable = false, updatable = false)
    private UUID couponId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gas_type_id")
    private GasType gasType;

    @Column(name = "liters", nullable = false)
    private double liters;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "expiration_date")
    private OffsetDateTime expirationDate;

    @Column(name = "use_date")
    private OffsetDateTime useDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Coupon() {}

    public Coupon(User customer, GasType gasType, double liters, boolean active, OffsetDateTime expirationDate, OffsetDateTime createdAt) {
        this.customer = customer;
        this.gasType = gasType;
        this.liters = liters;
        this.active = active;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public UUID getCouponId() { return couponId; }
    public void setCouponId(UUID couponId) { this.couponId = couponId; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public GasType getGasType() { return gasType; }
    public void setGasType(GasType gasType) { this.gasType = gasType; }
    public double getLiters() { return liters; }
    public void setLiters(double liters) { this.liters = liters; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public OffsetDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(OffsetDateTime expirationDate) { this.expirationDate = expirationDate; }
    public OffsetDateTime getUseDate() { return useDate; }
    public void setUseDate(OffsetDateTime useDate) { this.useDate = useDate; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
