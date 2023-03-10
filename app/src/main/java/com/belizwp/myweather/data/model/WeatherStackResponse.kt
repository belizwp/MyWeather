package com.belizwp.myweather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherStackResponse(
    @SerializedName("success")
    val success: Boolean = true,
    @SerializedName("error")
    val error: Error? = null,
    @SerializedName("request")
    val request: Request? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("current")
    val current: Current? = null,
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
    val weatherIcons: List<String> = emptyList(),
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String> = emptyList(),
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
