package com.gasstation.data.repository

import com.gasstation.data.api.AuthResponse
import com.gasstation.data.api.GasStationApi
import com.gasstation.data.api.LoginRequest
import com.gasstation.data.api.RegisterRequest
import com.gasstation.data.local.TokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: GasStationApi,
    private val tokenDataStore: TokenDataStore
) {
    val userEmail: Flow<String?> = tokenDataStore.userEmail
    val userFullName: Flow<String?> = tokenDataStore.userFullName
    val userRole: Flow<String?> = tokenDataStore.userRole

    suspend fun login(email: String, password: String): AuthResponse {
        val response = api.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            val body = response.body()!!
            tokenDataStore.saveSession(body.jwtToken, body.id, body.email, body.fullName, body.role)
            return body
        } else {
            throw Exception("Невірний email або пароль (${response.code()})")
        }
    }

    suspend fun register(email: String, password: String, fullName: String): AuthResponse {
        val response = api.register(RegisterRequest(email, password, fullName))
        if (response.isSuccessful) {
            val body = response.body()!!
            tokenDataStore.saveSession(body.jwtToken, body.id, body.email, body.fullName, body.role)
            return body
        } else {
            val errMsg = response.errorBody()?.string() ?: "Помилка реєстрації"
            throw Exception(errMsg)
        }
    }

    suspend fun logout() {
        tokenDataStore.clearSession()
    }

    suspend fun isLoggedIn(): Boolean {
        return !tokenDataStore.jwtToken.first().isNullOrEmpty()
    }
}
