package com.belizwp.myweather.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.domain.GetWeatherByCityNameUseCase
import com.belizwp.myweather.domain.Weather
import kotlinx.coroutines.launch

const val TAG = "MyWeatherViewModel"

class MyWeatherViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
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
        isLoading.value = true
        viewModelScope.launch {
            try {
                weather.value = getWeatherByCityNameUseCase(query.value)
                onSearchSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                onSearchError(e.message ?: "Unknown error")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun refresh(
        onRefreshSuccess: () -> Unit = {},
        onRefreshError: (String) -> Unit = {},
    ) {
        isRefreshing.value = true
        viewModelScope.launch {
            try {
                weather.value = getWeatherByCityNameUseCase(query.value)
                onRefreshSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                onRefreshError(e.message ?: "Unknown error")
            } finally {
                isRefreshing.value = false
            }
        }
    }

    private fun validateQuery(query: String): Boolean {
        return query.isNotBlank()
    }

}
