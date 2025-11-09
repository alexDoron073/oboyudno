package com.example.weather.weather.presentation.state

import com.example.weather.weather.domain.model.WeatherDay

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherDays: List<WeatherDay> = emptyList(),
    val error: String? = null
)

