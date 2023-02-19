package com.belizwp.myweather.data

import com.belizwp.myweather.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

data class Weather(
    val cityName: String = "",
    val country: String = "",
    val temperature: String = "",
    val weatherIconUrls: List<String> = listOf(),
    val humidity: String = "",
    val pressure: String = "",
    val uvIndex: String = "",
    val windSpeed: String = ""
)

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): Weather
}

class RemoteWeatherRepository(
    private val weatherStackApiService: WeatherStackApiService,
) : WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): Weather {
        val resp = weatherStackApiService.getCurrentWeather(
            accessKey = "replaceme",
            query = cityName,
        )
        return Weather(
            cityName = resp.location.name,
            country = resp.location.country,
            temperature = resp.current.temperature.toString(),
            weatherIconUrls = resp.current.weatherIcons,
            humidity = resp.current.humidity.toString(),
            pressure = resp.current.pressure.toString(),
            uvIndex = resp.current.uvIndex.toString(),
            windSpeed = resp.current.windSpeed.toString(),
        )
    }
}

interface WeatherStackApiService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") accessKey: String,
        @Query("query") query: String,
    ): WeatherStackResponse

}

@Serializable
data class WeatherStackResponse(
    @SerialName(value = "request")
    val request: Request,
    @SerialName(value = "location")
    val location: Location,
    @SerialName(value = "current")
    val current: Current,
)

@Serializable
data class Request(
    @SerialName(value = "type")
    val type: String,
    @SerialName(value = "query")
    val query: String,
    @SerialName(value = "language")
    val language: String,
    @SerialName(value = "unit")
    val unit: String
)

@Serializable
data class Location(
    @SerialName(value = "name")
    val name: String,
    @SerialName(value = "country")
    val country: String,
    @SerialName(value = "region")
    val region: String,
    @SerialName(value = "lat")
    val lat: String,
    @SerialName(value = "lon")
    val lon: String,
    @SerialName(value = "timezone_id")
    val timezoneId: String,
    @SerialName(value = "localtime")
    val localtime: String,
    @SerialName(value = "localtime_epoch")
    val localtimeEpoch: Int,
    @SerialName(value = "utc_offset")
    val utcOffset: String
)

@Serializable
data class Current(
    @SerialName(value = "observation_time")
    val observationTime: String,
    @SerialName(value = "temperature")
    val temperature: Int,
    @SerialName(value = "weather_code")
    val weatherCode: Int,
    @SerialName(value = "weather_icons")
    val weatherIcons: ArrayList<String> = arrayListOf(),
    @SerialName(value = "weather_descriptions")
    val weatherDescriptions: ArrayList<String> = arrayListOf(),
    @SerialName(value = "wind_speed")
    val windSpeed: Int,
    @SerialName(value = "wind_degree")
    val windDegree: Int,
    @SerialName(value = "wind_dir")
    val windDir: String,
    @SerialName(value = "pressure")
    val pressure: Int,
    @SerialName(value = "precip")
    val precip: Int,
    @SerialName(value = "humidity")
    val humidity: Int,
    @SerialName(value = "cloudcover")
    val cloudcover: Int,
    @SerialName(value = "feelslike")
    val feelslike: Int,
    @SerialName(value = "uv_index")
    val uvIndex: Int,
    @SerialName(value = "visibility")
    val visibility: Int,
    @SerialName(value = "is_day")
    val isDay: String
)
