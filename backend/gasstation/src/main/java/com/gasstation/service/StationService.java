package com.gasstation.service;

import com.gasstation.exceptions.NotFoundException;
import com.gasstation.model.dto.station.CreateStationRequest;
import com.gasstation.model.dto.station.StationDto;
import com.gasstation.model.dto.station.UpdateStationRequest;
import com.gasstation.model.entity.Station;
import com.gasstation.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public StationDto toDto(Station s) {
        return new StationDto(s.getStationId(), s.getCity(), s.getAddress(), s.getWorkTime(),
                s.getDescription(), s.getEquipmentList(), s.getCreatedAt());
    }

    public List<StationDto> getAll(String city, String address) {
        List<Station> stations = (city == null && address == null)
                ? stationRepository.findAll()
                : stationRepository.findByCityContainingIgnoreCaseAndAddressContainingIgnoreCase(
                        city == null ? "" : city, address == null ? "" : address);
        return stations.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Station getEntity(UUID id) {
        return stationRepository.findById(id).orElseThrow(() -> new NotFoundException("Station not found"));
    }

    public StationDto getById(UUID id) {
        return toDto(getEntity(id));
    }

    public StationDto create(CreateStationRequest r) {
        Station s = new Station(r.getCity(), r.getAddress(), r.getWorkTime(), r.getDescription(),
                r.getEquipmentList(), OffsetDateTime.now());
        return toDto(stationRepository.save(s));
    }

    public StationDto update(UUID id, UpdateStationRequest r) {
        Station s = getEntity(id);
        s.setCity(r.getCity());
        s.setAddress(r.getAddress());
        s.setWorkTime(r.getWorkTime());
        s.setDescription(r.getDescription());
        s.setEquipmentList(r.getEquipmentList());
        return toDto(stationRepository.save(s));
    }

    @Transactional
    public void delete(UUID id) {
        if (!stationRepository.existsById(id)) throw new NotFoundException("Station not found");
        stationRepository.deleteById(id);
    }
}
