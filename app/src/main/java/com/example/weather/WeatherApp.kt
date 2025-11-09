package com.example.weather

import android.app.Application
import com.example.weather.di.appModule
import com.example.weather.di.authModule
import com.example.weather.di.calculatorModule
import com.example.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@WeatherApp)
            modules(
                appModule,
                authModule,
                weatherModule,
                calculatorModule
            )
        }
    }
}

