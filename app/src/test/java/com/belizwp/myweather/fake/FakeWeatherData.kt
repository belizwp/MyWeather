package com.belizwp.myweather.fake

import com.belizwp.myweather.data.model.WeatherData

object FakeWeatherData {
    val weatherData = WeatherData(
        cityName = "London",
        country = "UK",
        temperature = 20,
        weatherIconUrl = "https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png",
        humidity = 80,
        pressure = 1000,
        uvIndex = 5,
        windSpeed = 10,
        windDir = "N",
        lat = 51.5074,
        lon = 0.1278,
    )
}
