package com.gasstation.service;

import com.gasstation.exceptions.NotFoundException;
import com.gasstation.model.dto.gastype.GasTypeDto;
import com.gasstation.model.dto.gastype.GasTypeRequest;
import com.gasstation.model.entity.GasType;
import com.gasstation.repository.GasTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GasTypeService {
    private final GasTypeRepository gasTypeRepository;

    public GasTypeService(GasTypeRepository gasTypeRepository) {
        this.gasTypeRepository = gasTypeRepository;
    }

    public GasTypeDto toDto(GasType g) {
        return new GasTypeDto(g.getGasTypeId(), g.getName(), g.getCreatedAt());
    }

    public List<GasTypeDto> getAll(String name) {
        List<GasType> list = (name == null)
                ? gasTypeRepository.findAll()
                : gasTypeRepository.findByNameContainingIgnoreCase(name);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public GasType getEntity(UUID id) {
        return gasTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Gas type not found"));
    }

    public GasTypeDto getById(UUID id) {
        return toDto(getEntity(id));
    }

    public GasTypeDto create(GasTypeRequest r) {
        GasType g = new GasType(r.getName(), OffsetDateTime.now());
        return toDto(gasTypeRepository.save(g));
    }

    public GasTypeDto update(UUID id, GasTypeRequest r) {
        GasType g = getEntity(id);
        g.setName(r.getName());
        return toDto(gasTypeRepository.save(g));
    }

    @Transactional
    public void delete(UUID id) {
        if (!gasTypeRepository.existsById(id)) throw new NotFoundException("Gas type not found");
        gasTypeRepository.deleteById(id);
    }
}
