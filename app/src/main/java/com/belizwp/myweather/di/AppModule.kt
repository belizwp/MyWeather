package com.belizwp.myweather.di

import com.belizwp.myweather.data.WeatherRepository
import com.belizwp.myweather.data.WeatherStackApiService
import com.belizwp.myweather.ui.MyWeatherViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    factory { provideOkHttpClient() }
    single { provideWeatherStackApi(get()) }
    single {
        WeatherRepository(get())
    }
    viewModel {
        MyWeatherViewModel(get())
    }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideWeatherStackApi(okHttpClient: OkHttpClient): WeatherStackApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.weatherstack.com")
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    return retrofit.create(WeatherStackApiService::class.java)
}
