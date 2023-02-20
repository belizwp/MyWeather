package com.belizwp.myweather.domain.usecase

import com.belizwp.myweather.data.repository.IWeatherRepository
import com.belizwp.myweather.domain.model.Weather
import kotlinx.coroutines.delay

class GetWeatherByCityNameUseCase(
    private val weatherRepository: IWeatherRepository,
) {
    suspend operator fun invoke(cityName: String): Weather {
//        val weatherData = weatherRepository.getWeatherByCityName(cityName)
//        return Weather(
//            cityName = weatherData.cityName,
//            country = weatherData.country,
//            temperature = formatTemperature(weatherData.temperature),
//            weatherIconUrl = weatherData.weatherIconUrl,
//            humidity = formatHumidity(weatherData.humidity),
//            pressure = formatPressure(weatherData.pressure),
//            uvIndex = formatUvIndex(weatherData.uvIndex),
//            wind = formatWind(weatherData.windSpeed, weatherData.windDir),
//            lat = weatherData.lat,
//            lon = weatherData.lon,
//        )
        delay(500)
        return mockWeather
    }
}

private fun formatTemperature(temperature: Int): String {
    return "${temperature}°C"
}

private fun formatHumidity(humidity: Int): String {
    return "${humidity}%"
}

private fun formatPressure(pressure: Int): String {
    return "$pressure mBar"
}

private fun formatUvIndex(uvIndex: Int): String {
    val uvLevel = when (uvIndex) {
        in 0..2 -> "Low"
        in 3..5 -> "Moderate"
        in 6..7 -> "High"
        in 8..10 -> "Very High"
        else -> "Extreme"
    }
    return "${uvLevel}, $uvIndex"
}

private fun formatWind(windSpeed: Int, windDir: String): String {
    return "${windSpeed}km/h $windDir"
}

val mockWeather = Weather(
    cityName = "Kuala Lumpur",
    country = "MY",
    temperature = "30°C",
    weatherIconUrl = "https://openweathermap.org/img/w/01d.png",
    humidity = "70%",
    pressure = "1000 mBar",
    uvIndex = "Low, 0",
    wind = "10km/h E",
    lat = 3.1390,
    lon = 101.6869,
)
