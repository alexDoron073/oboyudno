package com.example.weather.weather.data.repository

import com.example.weather.weather.data.dto.WeatherResponse
import com.example.weather.weather.data.remote.WeatherApiService
import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val apiService: WeatherApiService
) : WeatherRepository {
    
    override suspend fun getWeatherForecast(latitude: Double, longitude: Double): Result<List<WeatherDay>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWeatherForecast(latitude, longitude)
                val weatherDays = convertToWeatherDays(response)
                Result.success(weatherDays)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun convertToWeatherDays(response: WeatherResponse): List<WeatherDay> {
        val dailyData = response.daily
        val hourlyData = response.hourly

        return dailyData.time.mapIndexed { index, date ->
            val maxTemp = dailyData.maxTemperature.getOrNull(index) ?: 0.0
            val minTemp = dailyData.minTemperature.getOrNull(index) ?: 0.0
            val avgTemp = (maxTemp + minTemp) / 2.0
            val precipitation = dailyData.precipitation.getOrNull(index) ?: 0.0
            val weatherCode = dailyData.weatherCode.getOrNull(index) ?: 0
            val windSpeed = dailyData.windSpeed.getOrNull(index) ?: 0.0

            val datePrefix = date
            val matchingHourlyIndices = hourlyData.time.mapIndexedNotNull { hourIndex, hourTime ->
                if (hourTime.startsWith(datePrefix)) hourIndex else null
            }

            val avgHumidity = if (matchingHourlyIndices.isNotEmpty()) {
                matchingHourlyIndices.mapNotNull { idx ->
                    hourlyData.humidity.getOrNull(idx)
                }.average().toInt()
            } else {
                50
            }

            val avgPressure = if (matchingHourlyIndices.isNotEmpty()) {
                matchingHourlyIndices.mapNotNull { idx ->
                    hourlyData.pressure.getOrNull(idx)
                }.average()
            } else {
                1013.0
            }

            WeatherDay(
                date = date,
                maxTemp = maxTemp,
                minTemp = minTemp,
                avgTemp = avgTemp,
                precipitation = precipitation,
                weatherCode = weatherCode,
                windSpeed = windSpeed,
                humidity = avgHumidity,
                pressure = avgPressure
            )
        }
    }
}

