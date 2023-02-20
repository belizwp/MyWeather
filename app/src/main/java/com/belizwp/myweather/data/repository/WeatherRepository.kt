package com.belizwp.myweather.data.repository

import com.belizwp.myweather.BuildConfig
import com.belizwp.myweather.data.model.WeatherData
import com.belizwp.myweather.data.service.WeatherStackApiService

interface IWeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): WeatherData
}

class WeatherRepository(
    private val weatherStackApiService: WeatherStackApiService,
) : IWeatherRepository{
    override suspend fun getWeatherByCityName(cityName: String): WeatherData {
        val resp = weatherStackApiService.getCurrentWeather(
            accessKey = BuildConfig.WEATHER_STACK_ACCESS_KEY,
            query = cityName,
        )
        if (resp.error != null) {
            throw Exception("${resp.error.code}: ${resp.error.type}")
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
