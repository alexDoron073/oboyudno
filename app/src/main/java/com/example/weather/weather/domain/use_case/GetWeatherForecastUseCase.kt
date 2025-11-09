package com.example.weather.weather.domain.use_case

import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.domain.repository.WeatherRepository

class GetWeatherForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<List<WeatherDay>> {
        return repository.getWeatherForecast(latitude, longitude)
    }
}

