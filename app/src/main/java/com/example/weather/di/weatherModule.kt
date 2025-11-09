package com.example.weather.di

import com.example.weather.weather.data.remote.WeatherApiService
import com.example.weather.weather.data.repository.WeatherRepositoryImpl
import com.example.weather.weather.domain.repository.WeatherRepository
import com.example.weather.weather.domain.use_case.GetWeatherForecastUseCase
import com.example.weather.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val weatherModule = module {
    single<WeatherApiService> {
        get<Retrofit>().create(WeatherApiService::class.java)
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }

    single {
        GetWeatherForecastUseCase(get())
    }

    viewModel {
        WeatherViewModel(get())
    }
}

