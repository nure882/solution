package com.gasstation.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstation.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    fun onFullNameChange(v: String) { _state.value = _state.value.copy(fullName = v, error = null) }
    fun onEmailChange(v: String) { _state.value = _state.value.copy(email = v, error = null) }
    fun onPasswordChange(v: String) { _state.value = _state.value.copy(password = v, error = null) }

    fun register() {
        val c = _state.value
        if (c.email.isBlank() || c.password.isBlank() || c.fullName.isBlank()) {
            _state.value = c.copy(error = "Заповніть усі поля")
            return
        }
        if (c.password.length < 6) {
            _state.value = c.copy(error = "Пароль має містити щонайменше 6 символів")
            return
        }
        viewModelScope.launch {
            _state.value = c.copy(isLoading = true, error = null)
            try {
                authRepository.register(c.email.trim(), c.password, c.fullName.trim())
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = "Помилка реєстрації. Email може бути зайнятий.")
            }
        }
    }
}
