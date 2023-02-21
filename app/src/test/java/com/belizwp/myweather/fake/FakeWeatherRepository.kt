package com.belizwp.myweather.fake

import com.belizwp.myweather.data.model.WeatherData
import com.belizwp.myweather.data.repository.WeatherRepository

class FakeWeatherRepository : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): WeatherData {
        return FakeWeatherData.weatherData.copy(cityName = cityName)
    }
}
