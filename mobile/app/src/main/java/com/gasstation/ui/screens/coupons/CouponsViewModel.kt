package com.gasstation.ui.screens.coupons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstation.data.api.CouponDto
import com.gasstation.data.repository.CouponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CouponsUiState(
    val coupons: List<CouponDto> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val couponRepository: CouponRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CouponsUiState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                _state.value = CouponsUiState(coupons = couponRepository.getMyCoupons(), isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
