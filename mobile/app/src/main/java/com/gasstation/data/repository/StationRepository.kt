package com.gasstation.data.repository

import com.gasstation.data.api.GasStationApi
import com.gasstation.data.api.PumpDto
import com.gasstation.data.api.StationDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepository @Inject constructor(
    private val api: GasStationApi
) {
    suspend fun getStations(query: String = ""): List<StationDto> {
        val response = api.getStations(city = query.ifBlank { null })
        if (response.isSuccessful) return response.body() ?: emptyList()
        throw Exception("Помилка завантаження станцій (${response.code()})")
    }

    suspend fun getStation(id: String): StationDto {
        val response = api.getStation(id)
        if (response.isSuccessful) return response.body()!!
        throw Exception("Станцію не знайдено (${response.code()})")
    }

    suspend fun getPumps(stationId: String): List<PumpDto> {
        val response = api.getPumps(stationId)
        if (response.isSuccessful) return response.body() ?: emptyList()
        throw Exception("Помилка завантаження колонок (${response.code()})")
    }
}
