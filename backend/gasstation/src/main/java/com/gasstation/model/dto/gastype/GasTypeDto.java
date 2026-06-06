package com.gasstation.model.dto.gastype;

import java.time.OffsetDateTime;
import java.util.UUID;

public class GasTypeDto {
    private UUID gasTypeId;
    private String name;
    private OffsetDateTime createdAt;

    public GasTypeDto(UUID gasTypeId, String name, OffsetDateTime createdAt) {
        this.gasTypeId = gasTypeId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getGasTypeId() { return gasTypeId; }
    public String getName() { return name; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
