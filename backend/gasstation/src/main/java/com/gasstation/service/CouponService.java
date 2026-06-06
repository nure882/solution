package com.gasstation.service;

import com.gasstation.exceptions.NotFoundException;
import com.gasstation.model.dto.coupon.CouponDto;
import com.gasstation.model.dto.coupon.CreateCouponRequest;
import com.gasstation.model.dto.coupon.UpdateCouponRequest;
import com.gasstation.model.entity.Coupon;
import com.gasstation.model.entity.GasType;
import com.gasstation.model.entity.User;
import com.gasstation.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final GasTypeService gasTypeService;
    private final UserService userService;

    public CouponService(CouponRepository couponRepository, GasTypeService gasTypeService, UserService userService) {
        this.couponRepository = couponRepository;
        this.gasTypeService = gasTypeService;
        this.userService = userService;
    }

    public CouponDto toDto(Coupon c) {
        return new CouponDto(
                c.getCouponId(),
                c.getCustomer() == null ? null : c.getCustomer().getUserId(),
                c.getCustomer() == null ? null : c.getCustomer().getEmail(),
                c.getGasType().getGasTypeId(),
                c.getGasType().getName(),
                c.getLiters(),
                c.isActive(),
                c.getExpirationDate(),
                c.getUseDate());
    }

    public List<CouponDto> getAll() {
        return couponRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<CouponDto> getByCustomer(UUID customerId) {
        return couponRepository.findByCustomer_UserId(customerId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public Coupon getEntity(UUID id) {
        return couponRepository.findById(id).orElseThrow(() -> new NotFoundException("Coupon not found"));
    }

    public CouponDto getById(UUID id) {
        return toDto(getEntity(id));
    }

    public CouponDto create(CreateCouponRequest r) {
        GasType gasType = gasTypeService.getEntity(r.getGasTypeId());
        User customer = r.getCustomerId() == null ? null : userService.getUserById(r.getCustomerId());
        Coupon c = new Coupon(customer, gasType, r.getLiters(), true, r.getExpirationDate(), OffsetDateTime.now());
        return toDto(couponRepository.save(c));
    }

    public CouponDto update(UUID id, UpdateCouponRequest r) {
        Coupon c = getEntity(id);
        if (r.getGasTypeId() != null) c.setGasType(gasTypeService.getEntity(r.getGasTypeId()));
        c.setLiters(r.getLiters());
        if (r.getActive() != null) c.setActive(r.getActive());
        c.setExpirationDate(r.getExpirationDate());
        c.setUseDate(r.getUseDate());
        return toDto(couponRepository.save(c));
    }

    @Transactional
    public void delete(UUID id) {
        if (!couponRepository.existsById(id)) throw new NotFoundException("Coupon not found");
        couponRepository.deleteById(id);
    }
}
