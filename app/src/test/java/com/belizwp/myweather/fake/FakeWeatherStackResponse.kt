package com.belizwp.myweather.fake

import com.belizwp.myweather.data.model.*

object FakeWeatherStackResponse {
    val successResponse = WeatherStackResponse(
        success = true,
        error = null,
        request = Request(
            type = "City",
            query = "London",
            language = "en",
            unit = "m"
        ),
        location = Location(
            name = "London",
            country = "United Kingdom",
            region = "City of London, Greater London",
            lat = "51.517",
            lon = "-0.106",
            timezoneId = "Europe/London",
            localtime = "2021-03-01 16:00",
            localtimeEpoch = 1614596800,
            utcOffset = "0.0"
        ),
        current = Current(
            observationTime = "03:00 PM",
            temperature = 7,
            weatherCode = 113,
            weatherIcons = listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"),
            weatherDescriptions = listOf("Sunny"),
            windSpeed = 7,
            windDegree = 80,
            windDir = "E",
            pressure = 1020,
            precip = 0,
            humidity = 50,
            cloudcover = 0,
            feelslike = 7,
            uvIndex = 1,
            visibility = 10,
            isDay = "yes"
        )
    )

    val errorResponse = WeatherStackResponse(
        success = false,
        error = Error(
            code = 1006,
            type = "invalid_access_key",
            info = "You have not supplied a valid API Access Key",
        ),
    )
}