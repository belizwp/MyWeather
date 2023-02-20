package com.belizwp.myweather.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belizwp.myweather.domain.usecase.GetWeatherByCityNameUseCase
import com.belizwp.myweather.domain.model.Weather
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

const val TAG = "MyWeatherViewModel"

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

    var weather = mutableStateOf(Weather())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var query = mutableStateOf("")
        private set

    var isQueryValid = mutableStateOf(true)
        private set

    fun updateQuery(query: String) {
        this.query.value = query
        isQueryValid.value = validateQuery(query)
    }

    fun search() {
        isQueryValid.value = validateQuery(query.value)
        if (!isQueryValid.value) {
            return
        }
        viewModelScope.launch {
            isLoading.value = true
            try {
                weather.value = getWeatherByCityNameUseCase(query.value)
                eventChannel.send(Event.SearchSucceeded)
            } catch (e: Exception) {
                Log.e(TAG, "search: ", e)
                eventChannel.send(Event.SearchFailed(e.message ?: "Unknown error"))
            } finally {
                isLoading.value = false
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            try {
                weather.value = getWeatherByCityNameUseCase(query.value)
                eventChannel.send(Event.RefreshSucceeded)
            } catch (e: Exception) {
                Log.e(TAG, "refresh: ", e)
                eventChannel.send(Event.RefreshFailed(e.message ?: "Unknown error"))
            } finally {
                isRefreshing.value = false
            }
        }
    }

    private fun validateQuery(query: String): Boolean {
        return query.isNotBlank()
    }

}
