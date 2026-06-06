package com.gasstation.data.api

// Auth
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val password: String, val fullName: String)
data class AuthResponse(
    val jwtToken: String,
    val id: String,
    val email: String,
    val fullName: String,
    val role: String = "CUSTOMER"
)

// Stations
data class StationDto(
    val stationId: String,
    val city: String,
    val address: String,
    val workTime: String? = null,
    val description: String? = null,
    val equipmentList: String? = null
)

// Pumps
data class PumpDto(
    val pumpId: String,
    val number: Int,
    val gasCount: Double,
    val cost: Double,
    val stationId: String,
    val gasTypeId: String,
    val gasTypeName: String
)

// Coupons
data class CouponDto(
    val couponId: String,
    val customerId: String? = null,
    val customerEmail: String? = null,
    val gasTypeId: String,
    val gasTypeName: String,
    val liters: Double,
    val active: Boolean = true,
    val expirationDate: String? = null,
    val useDate: String? = null
)
