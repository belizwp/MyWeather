package com.belizwp.myweather.domain.model

data class Weather(
    val cityName: String = "",
    val country: String = "",
    val temperature: String = "",
    val weatherIconUrl: String = "",
    val humidity: String = "",
    val pressure: String = "",
    val uvIndex: String = "",
    val wind: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)
