package com.gasstation.ui.screens.stationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstation.data.api.PumpDto
import com.gasstation.data.api.StationDto
import com.gasstation.data.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StationDetailUiState(
    val station: StationDto? = null,
    val pumps: List<PumpDto> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StationDetailUiState())
    val state = _state.asStateFlow()

    fun load(stationId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val station = stationRepository.getStation(stationId)
                val pumps = stationRepository.getPumps(stationId)
                _state.value = StationDetailUiState(station = station, pumps = pumps, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
