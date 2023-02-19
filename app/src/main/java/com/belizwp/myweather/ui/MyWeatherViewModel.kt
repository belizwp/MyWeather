package com.belizwp.myweather.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.data.Weather
import com.belizwp.myweather.data.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "MyWeatherViewModel"

data class SearchState(
    val query: String = "",
    val isQueryValid: Boolean = true,
)

sealed interface WeatherState {
    object Loading : WeatherState
    data class Success(val cityName: String = "") : WeatherState
    object Error : WeatherState
}

class MyWeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    var weather = mutableStateOf(Weather())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var query = mutableStateOf("")
        private set

    fun updateQuery(query: String) {
        this.query.value = query
    }

    fun search(
        onSearchSuccess: () -> Unit = {},
        onSearchError: (String) -> Unit = {},
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                weather.value = weatherRepository.getWeatherByCityName(query.value)
                isLoading.value = false
                onSearchSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                isLoading.value = false
                onSearchError(e.message ?: "Unknown error")
            }
        }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch {
            delay(400)
            isRefreshing.value = false
        }
    }

    private fun validateQuery(query: String): Boolean {
        return query.isNotBlank()
    }

}
