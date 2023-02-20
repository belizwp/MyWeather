package com.belizwp.myweather.data.service

import com.belizwp.myweather.data.model.WeatherStackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherStackApiService {
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") accessKey: String,
        @Query("query") query: String,
    ): WeatherStackResponse
}
