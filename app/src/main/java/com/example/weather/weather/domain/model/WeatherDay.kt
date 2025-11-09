package com.example.weather.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDay(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val avgTemp: Double,
    val precipitation: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Double
) : Parcelable

