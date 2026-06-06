package com.gasstation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstation.data.api.StationDto
import com.gasstation.data.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val stations: List<StationDto> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val search: String = ""
) {
    val filtered: List<StationDto>
        get() = if (search.isBlank()) stations
        else stations.filter {
            it.city.contains(search, true) || it.address.contains(search, true)
        }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init { loadStations() }

    fun onSearchChange(q: String) { _state.value = _state.value.copy(search = q) }

    fun loadStations() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val stations = stationRepository.getStations()
                _state.value = _state.value.copy(stations = stations, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
