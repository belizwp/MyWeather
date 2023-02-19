package com.belizwp.myweather.data

import com.belizwp.myweather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.annotations.SerializedName

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

class WeatherRepository(
    private val weatherStackApiService: WeatherStackApiService,
) {
    suspend fun getWeatherByCityName(cityName: String): Weather {
        val resp = weatherStackApiService.getCurrentWeather(
            accessKey = BuildConfig.WEATHER_STACK_ACCESS_KEY,
            query = cityName,
        )
        if (resp.error != null) {
            throw Exception("${resp.error.code}: ${resp.error.type}")
        }
        return Weather(
            cityName = resp.location.name,
            country = resp.location.country,
            temperature = formatTemperature(resp.current.temperature),
            weatherIconUrl = resp.current.weatherIcons.firstOrNull() ?: "",
            humidity = formatHumidity(resp.current.humidity),
            pressure = formatPressure(resp.current.pressure),
            uvIndex = formatUvIndex(resp.current.uvIndex),
            wind = formatWind(resp.current.windSpeed, resp.current.windDir),
            lat = resp.location.lat.toDouble(),
            lon = resp.location.lon.toDouble(),
        )
    }
}

private fun formatTemperature(temperature: Int): String {
    return "${temperature}°C"
}

private fun formatHumidity(humidity: Int): String {
    return "${humidity}%"
}

private fun formatPressure(pressure: Int): String {
    return "${pressure} mBar"
}

private fun formatUvIndex(uvIndex: Int): String {
    val uvLevel = when (uvIndex) {
        in 0..2 -> "Low"
        in 3..5 -> "Moderate"
        in 6..7 -> "High"
        in 8..10 -> "Very High"
        else -> "Extreme"
    }
    return "${uvLevel}, ${uvIndex}"
}

private fun formatWind(windSpeed: Int, windDir: String): String {
    return "${windSpeed}km/h ${windDir}"
}

interface WeatherStackApiService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") accessKey: String,
        @Query("query") query: String,
    ): WeatherStackResponse

}

data class WeatherStackResponse(
    @SerializedName("success")
    val success: Boolean = true,
    @SerializedName("error")
    val error: Error? = null,
    @SerializedName("request")
    val request: Request,
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current,
)

data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("info")
    val info: String
)

data class Request(
    @SerializedName("type")
    val type: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("unit")
    val unit: String
)

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("utc_offset")
    val utcOffset: String
)

data class Current(
    @SerializedName("observation_time")
    val observationTime: String,
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_icons")
    val weatherIcons: ArrayList<String> = arrayListOf(),
    @SerializedName("weather_descriptions")
    val weatherDescriptions: ArrayList<String> = arrayListOf(),
    @SerializedName("wind_speed")
    val windSpeed: Int,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("precip")
    val precip: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("cloudcover")
    val cloudcover: Int,
    @SerializedName("feelslike")
    val feelslike: Int,
    @SerializedName("uv_index")
    val uvIndex: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("is_day")
    val isDay: String
)
