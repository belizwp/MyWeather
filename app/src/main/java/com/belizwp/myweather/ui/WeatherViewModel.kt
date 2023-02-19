package com.belizwp.myweather.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.data.WeatherUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Success())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    var query = mutableStateOf("")
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch {
            delay(400)
            isRefreshing.value = false
            _uiState.value = WeatherUiState.Success()
        }
    }

    fun updateQuery(query: String) {
        this.query.value = query
    }

}
