package com.belizwp.myweather.data.repository

import com.belizwp.myweather.data.model.WeatherData

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): WeatherData
}
