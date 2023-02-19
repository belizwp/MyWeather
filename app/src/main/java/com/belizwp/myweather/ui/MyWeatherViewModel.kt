package com.belizwp.myweather.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.data.SearchUiState
import com.belizwp.myweather.data.WeatherRepository
import com.belizwp.myweather.data.WeatherUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyWeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _weatherUiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Success())
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    private val _searchUiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    var isRefreshing = mutableStateOf(false)
        private set

    fun updateQuery(query: String) {
        _searchUiState.update {
            it.copy(
                query = query,
                isQueryValid = validateQuery(query),
            )
        }
    }

    fun findWeather(): Boolean {
        if (validateQuery(_searchUiState.value.query).not()) {
            _searchUiState.update {
                it.copy(
                    isQueryValid = false,
                )
            }
            return false
        }

        _weatherUiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            delay(400)
            _weatherUiState.value = WeatherUiState.Success()
        }
        return true
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch {
            delay(400)
            isRefreshing.value = false
            _weatherUiState.value = WeatherUiState.Success()
        }
    }

    private fun validateQuery(query: String): Boolean {
        return query.isNotBlank()
    }

}
