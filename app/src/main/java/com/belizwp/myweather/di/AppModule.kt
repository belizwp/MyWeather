package com.belizwp.myweather.di

import com.belizwp.myweather.BuildConfig
import com.belizwp.myweather.data.repository.WeatherRepository
import com.belizwp.myweather.data.repository.WeatherRepositoryImpl
import com.belizwp.myweather.data.service.WeatherStackApiService
import com.belizwp.myweather.domain.usecase.GetWeatherByCityNameUseCase
import com.belizwp.myweather.ui.MyWeatherViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    factory { provideOkHttpClient() }
    single { provideWeatherStackApi(get()) }
    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }
    single {
        GetWeatherByCityNameUseCase(get())
    }
    viewModel {
        MyWeatherViewModel(get())
    }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideWeatherStackApi(okHttpClient: OkHttpClient): WeatherStackApiService {
    val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.WEATHER_STACK_BASE_URL)
        .build()

    return retrofit.create(WeatherStackApiService::class.java)
}
