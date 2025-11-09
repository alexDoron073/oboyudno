package com.example.weather.weather.presentation.util

import com.example.weather.R
import com.example.weather.weather.domain.model.WeatherDay

object WeatherCodeMapper {
    fun getWeatherDescription(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Ясно"
            1, 2, 3 -> "Переменная облачность"
            45, 48 -> "Туман"
            51, 53, 55 -> "Небольшой дождь"
            56, 57 -> "Ледяной дождь"
            61, 63, 65 -> "Дождь"
            66, 67 -> "Ледяной дождь"
            71, 73, 75 -> "Снег"
            77 -> "Снежная крупа"
            80, 81, 82 -> "Ливень"
            85, 86 -> "Снегопад"
            95 -> "Гроза"
            96, 99 -> "Гроза с градом"
            else -> "Неизвестно"
        }
    }

    fun getWeatherIconRes(weatherCode: Int): Int {
        return when (weatherCode) {
            0 -> R.drawable.ic_sunny
            1, 2, 3 -> R.drawable.ic_partly_cloudy
            45, 48 -> R.drawable.ic_foggy
            51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> R.drawable.ic_rainy
            71, 73, 75, 77, 85, 86 -> R.drawable.ic_snowy
            95, 96, 99 -> R.drawable.ic_thunderstorm
            else -> R.drawable.ic_cloudy
        }
    }

    fun getBackgroundColorRes(weatherDay: WeatherDay): Int {
        val temp = weatherDay.avgTemp
        val hasPrecipitation = weatherDay.precipitation > 0.5

        return when {
            hasPrecipitation && weatherDay.weatherCode in 71..77 || weatherDay.weatherCode in 85..86 -> {
                R.color.weather_cold_light
            }
            hasPrecipitation -> {
                R.color.weather_cool_light
            }
            temp >= 30 -> R.color.weather_hot_light
            temp >= 25 -> R.color.weather_warm_light
            temp >= 15 -> R.color.weather_mild_light
            temp >= 5 -> R.color.weather_cool_light
            temp >= 0 -> R.color.weather_cold_light
            else -> R.color.weather_freezing_light
        }
    }

    fun getCardColorRes(weatherDay: WeatherDay): Int {
        val temp = weatherDay.avgTemp
        val hasPrecipitation = weatherDay.precipitation > 0.5

        return when {
            hasPrecipitation && weatherDay.weatherCode in 71..77 || weatherDay.weatherCode in 85..86 -> {
                R.color.weather_cold
            }
            hasPrecipitation -> {
                R.color.weather_cool
            }
            temp >= 30 -> R.color.weather_hot
            temp >= 25 -> R.color.weather_warm
            temp >= 15 -> R.color.weather_mild
            temp >= 5 -> R.color.weather_cool
            temp >= 0 -> R.color.weather_cold
            else -> R.color.weather_freezing
        }
    }
}

