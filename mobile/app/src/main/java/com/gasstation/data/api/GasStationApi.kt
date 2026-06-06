package com.gasstation.data.api

import retrofit2.Response
import retrofit2.http.*

interface GasStationApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @GET("api/stations")
    suspend fun getStations(
        @Query("city") city: String? = null,
        @Query("address") address: String? = null
    ): Response<List<StationDto>>

    @GET("api/stations/{id}")
    suspend fun getStation(@Path("id") id: String): Response<StationDto>

    @GET("api/pumps")
    suspend fun getPumps(@Query("stationId") stationId: String): Response<List<PumpDto>>

    @GET("api/coupons/my")
    suspend fun getMyCoupons(): Response<List<CouponDto>>
}
