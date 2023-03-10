package com.belizwp.myweather.data.repository

import com.belizwp.myweather.BuildConfig
import com.belizwp.myweather.data.model.WeatherData
import com.belizwp.myweather.data.service.WeatherStackApiService

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): WeatherData
}

class WeatherRepositoryImpl(
    private val weatherStackApiService: WeatherStackApiService,
) : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherData {
        val resp = weatherStackApiService.getCurrentWeather(
            accessKey = BuildConfig.WEATHER_STACK_ACCESS_KEY,
            query = cityName,
        )
        if (resp.error != null) {
            throw Exception("${resp.error.code}: ${resp.error.type}")
        }
        if (resp.location == null) {
            throw Exception("Location data is null")
        }
        if (resp.current == null) {
            throw Exception("Current data is null")
        }
        return WeatherData(
            cityName = resp.location.name,
            country = resp.location.country,
            temperature = resp.current.temperature,
            weatherIconUrl = resp.current.weatherIcons.firstOrNull() ?: "",
            humidity = resp.current.humidity,
            pressure = resp.current.pressure,
            uvIndex = resp.current.uvIndex,
            windSpeed = resp.current.windSpeed,
            windDir = resp.current.windDir,
            lat = resp.location.lat.toDouble(),
            lon = resp.location.lon.toDouble(),
        )
    }
}
