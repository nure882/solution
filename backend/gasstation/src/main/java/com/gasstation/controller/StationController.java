package com.gasstation.controller;

import com.gasstation.model.dto.station.CreateStationRequest;
import com.gasstation.model.dto.station.StationDto;
import com.gasstation.model.dto.station.UpdateStationRequest;
import com.gasstation.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/stations")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<List<StationDto>> getAll(@RequestParam(required = false) String city,
                                                   @RequestParam(required = false) String address) {
        return ResponseEntity.ok(stationService.getAll(city, address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(stationService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StationDto> create(@RequestBody CreateStationRequest request) {
        return ResponseEntity.ok(stationService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StationDto> update(@PathVariable UUID id, @RequestBody UpdateStationRequest request) {
        return ResponseEntity.ok(stationService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
