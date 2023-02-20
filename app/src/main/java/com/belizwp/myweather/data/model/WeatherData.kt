package com.belizwp.myweather.data.model

data class WeatherData(
    val cityName: String = "",
    val country: String = "",
    val temperature: Int = 0,
    val weatherIconUrl: String = "",
    val humidity: Int = 0,
    val pressure: Int = 0,
    val uvIndex: Int = 0,
    val windSpeed: Int = 0,
    val windDir: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)
