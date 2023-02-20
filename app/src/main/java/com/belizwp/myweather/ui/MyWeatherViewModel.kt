package com.belizwp.myweather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.domain.usecase.GetWeatherByCityNameUseCase
import com.belizwp.myweather.domain.model.Weather
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "MyWeatherViewModel"

data class MyWeatherUiState(
    val query: String = "",
    val isQueryValid: Boolean = true,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val weather: Weather = Weather(),
)

sealed interface Event {
    object SearchSucceeded : Event
    data class SearchFailed(val error: String) : Event
    object RefreshSucceeded : Event
    data class RefreshFailed(val error: String) : Event
}

class MyWeatherViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(MyWeatherUiState())
    val uiState = _uiState.asStateFlow()

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                isQueryValid = validateQuery(query),
            )
        }
    }

    fun search() {
        updateQuery(_uiState.value.query)

        if (!_uiState.value.isQueryValid) {
            return
        }

        viewModelScope.launch {
            setLoading(true)
            try {
                setWeather(getWeatherByCityNameUseCase(_uiState.value.query))
                eventChannel.send(Event.SearchSucceeded)
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                eventChannel.send(Event.SearchFailed(e.message ?: "Unknown error"))
            } finally {
                setLoading(false)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            setRefreshing(true)
            try {
                setWeather(getWeatherByCityNameUseCase(_uiState.value.query))
                eventChannel.send(Event.RefreshSucceeded)
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                eventChannel.send(Event.RefreshFailed(e.message ?: "Unknown error"))
            } finally {
                setRefreshing(false)
            }
        }
    }

    private fun setWeather(weather: Weather) {
        _uiState.update {
            it.copy(weather = weather)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        _uiState.update {
            it.copy(isRefreshing = isRefreshing)
        }
    }

    private fun validateQuery(query: String): Boolean {
        return query.isNotBlank()
    }

}
