package com.example.weather.weather.domain.repository

import com.example.weather.weather.domain.model.WeatherDay

interface WeatherRepository {
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): Result<List<WeatherDay>>
}

