package com.belizwp.myweather.data

sealed interface WeatherUiState {
    data class Success(
        val name: String = "",
        val country: String = "",
        val temperature: String = "",
        val weatherIconUrls: List<String> = listOf(),
        val humidity: String = "",
        val pressure: String = "",
        val uvIndex: String = "",
        val windSpeed: String = ""
    ) : WeatherUiState
    object Error : WeatherUiState
    object Loading : WeatherUiState
    object Refreshing : WeatherUiState
}
