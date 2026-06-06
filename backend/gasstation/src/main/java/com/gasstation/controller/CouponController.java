package com.gasstation.controller;

import com.gasstation.model.dto.coupon.CouponDto;
import com.gasstation.model.dto.coupon.CreateCouponRequest;
import com.gasstation.model.dto.coupon.UpdateCouponRequest;
import com.gasstation.model.entity.CustomUserDetails;
import com.gasstation.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @GetMapping
    public ResponseEntity<List<CouponDto>> getAll() {
        return ResponseEntity.ok(couponService.getAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<CouponDto>> getMy(Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(couponService.getByCustomer(details.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(couponService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PostMapping
    public ResponseEntity<CouponDto> create(@RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(couponService.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PutMapping("/{id}")
    public ResponseEntity<CouponDto> update(@PathVariable UUID id, @RequestBody UpdateCouponRequest request) {
        return ResponseEntity.ok(couponService.update(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
