package com.gasstation.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstation.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val fullName: String = "",
    val email: String = "",
    val role: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.userFullName.collect { _state.value = _state.value.copy(fullName = it ?: "") }
        }
        viewModelScope.launch {
            authRepository.userEmail.collect { _state.value = _state.value.copy(email = it ?: "") }
        }
        viewModelScope.launch {
            authRepository.userRole.collect { _state.value = _state.value.copy(role = it ?: "") }
        }
    }
}
