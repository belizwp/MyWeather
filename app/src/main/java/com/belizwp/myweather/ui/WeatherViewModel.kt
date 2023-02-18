package com.belizwp.myweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.data.WeatherUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Success())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    val isRefreshing = uiState.map { it is WeatherUiState.Refreshing }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)


    fun refresh() {
        _uiState.value = WeatherUiState.Refreshing
        viewModelScope.launch {
            delay(400)
            _uiState.value = WeatherUiState.Success()
        }
    }

}
