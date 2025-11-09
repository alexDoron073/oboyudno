package com.example.weather.weather.data.remote

import com.example.weather.weather.data.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m,relative_humidity_2m,precipitation,weather_code,wind_speed_10m,surface_pressure",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,precipitation_sum,weather_code,wind_speed_10m_max",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

