package com.gasstation.data.repository

import com.gasstation.data.api.CouponDto
import com.gasstation.data.api.GasStationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CouponRepository @Inject constructor(
    private val api: GasStationApi
) {
    suspend fun getMyCoupons(): List<CouponDto> {
        val response = api.getMyCoupons()
        if (response.isSuccessful) return response.body() ?: emptyList()
        throw Exception("Помилка завантаження талонів (${response.code()})")
    }
}
