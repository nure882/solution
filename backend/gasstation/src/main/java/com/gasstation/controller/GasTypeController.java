package com.gasstation.controller;

import com.gasstation.model.dto.gastype.GasTypeDto;
import com.gasstation.model.dto.gastype.GasTypeRequest;
import com.gasstation.service.GasTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/gas-types")
public class GasTypeController {
    private final GasTypeService gasTypeService;

    public GasTypeController(GasTypeService gasTypeService) {
        this.gasTypeService = gasTypeService;
    }

    @GetMapping
    public ResponseEntity<List<GasTypeDto>> getAll(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(gasTypeService.getAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GasTypeDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(gasTypeService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GasTypeDto> create(@RequestBody GasTypeRequest request) {
        return ResponseEntity.ok(gasTypeService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GasTypeDto> update(@PathVariable UUID id, @RequestBody GasTypeRequest request) {
        return ResponseEntity.ok(gasTypeService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        gasTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
