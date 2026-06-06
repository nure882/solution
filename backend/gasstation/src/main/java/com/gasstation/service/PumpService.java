package com.gasstation.service;

import com.gasstation.exceptions.NotFoundException;
import com.gasstation.model.dto.pump.CreatePumpRequest;
import com.gasstation.model.dto.pump.PumpDto;
import com.gasstation.model.dto.pump.UpdatePumpRequest;
import com.gasstation.model.entity.GasType;
import com.gasstation.model.entity.Pump;
import com.gasstation.model.entity.Station;
import com.gasstation.repository.PumpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PumpService {
    private final PumpRepository pumpRepository;
    private final StationService stationService;
    private final GasTypeService gasTypeService;

    public PumpService(PumpRepository pumpRepository, StationService stationService, GasTypeService gasTypeService) {
        this.pumpRepository = pumpRepository;
        this.stationService = stationService;
        this.gasTypeService = gasTypeService;
    }

    public PumpDto toDto(Pump p) {
        return new PumpDto(p.getPumpId(), p.getNumber(), p.getGasCount(), p.getCost(),
                p.getStation().getStationId(), p.getGasType().getGasTypeId(), p.getGasType().getName());
    }

    public List<PumpDto> getAll(UUID stationId) {
        List<Pump> pumps = (stationId == null)
                ? pumpRepository.findAll()
                : pumpRepository.findByStation_StationId(stationId);
        return pumps.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Pump getEntity(UUID id) {
        return pumpRepository.findById(id).orElseThrow(() -> new NotFoundException("Pump not found"));
    }

    public PumpDto getById(UUID id) {
        return toDto(getEntity(id));
    }

    public PumpDto create(CreatePumpRequest r) {
        Station station = stationService.getEntity(r.getStationId());
        GasType gasType = gasTypeService.getEntity(r.getGasTypeId());
        Pump p = new Pump(r.getNumber(), r.getGasCount(), r.getCost(), station, gasType, OffsetDateTime.now());
        return toDto(pumpRepository.save(p));
    }

    public PumpDto update(UUID id, UpdatePumpRequest r) {
        Pump p = getEntity(id);
        p.setNumber(r.getNumber());
        p.setGasCount(r.getGasCount());
        p.setCost(r.getCost());
        if (r.getGasTypeId() != null) {
            p.setGasType(gasTypeService.getEntity(r.getGasTypeId()));
        }
        return toDto(pumpRepository.save(p));
    }

    @Transactional
    public void delete(UUID id) {
        if (!pumpRepository.existsById(id)) throw new NotFoundException("Pump not found");
        pumpRepository.deleteById(id);
    }
}
