package com.example.weather.weather.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("hourly") val hourly: HourlyData,
    @SerializedName("daily") val daily: DailyData
)

data class HourlyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<Double>,
    @SerializedName("relative_humidity_2m") val humidity: List<Int>,
    @SerializedName("precipitation") val precipitation: List<Double>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("wind_speed_10m") val windSpeed: List<Double>,
    @SerializedName("surface_pressure") val pressure: List<Double>
)

data class DailyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m_max") val maxTemperature: List<Double>,
    @SerializedName("temperature_2m_min") val minTemperature: List<Double>,
    @SerializedName("precipitation_sum") val precipitation: List<Double>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("wind_speed_10m_max") val windSpeed: List<Double>
)

