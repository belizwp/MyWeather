package com.belizwp.myweather

import android.app.Application
import com.belizwp.myweather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MyWeatherApplication)
            // Load modules
            modules(appModule)
        }
    }
}