package com.belizwp.myweather.fake

import com.belizwp.myweather.domain.model.Weather

object FakeWeather {
    val weather = Weather(
        cityName = "London",
        country = "UK",
        temperature = "20",
        weatherIconUrl = "https://openweathermap.org/img/w/01d.png",
        humidity = "50",
        pressure = "1000",
        uvIndex = "5",
        wind = "10",
        lat = 51.5085,
        lon = -0.1257,
    )
}