package com.gasstation.controller;

import com.gasstation.model.dto.pump.CreatePumpRequest;
import com.gasstation.model.dto.pump.PumpDto;
import com.gasstation.model.dto.pump.UpdatePumpRequest;
import com.gasstation.service.PumpService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/pumps")
public class PumpController {
    private final PumpService pumpService;

    public PumpController(PumpService pumpService) {
        this.pumpService = pumpService;
    }

    @GetMapping
    public ResponseEntity<List<PumpDto>> getAll(@RequestParam(required = false) UUID stationId) {
        return ResponseEntity.ok(pumpService.getAll(stationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PumpDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(pumpService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PumpDto> create(@RequestBody CreatePumpRequest request) {
        return ResponseEntity.ok(pumpService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PumpDto> update(@PathVariable UUID id, @RequestBody UpdatePumpRequest request) {
        return ResponseEntity.ok(pumpService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pumpService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
