package com.example.weather.weather.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weather.R
import com.example.weather.databinding.ActivityWeatherDetailBinding
import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.presentation.util.WeatherCodeMapper
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherDetailBinding

    companion object {
        const val EXTRA_WEATHER_DAY = "extra_weather_day"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val weatherDay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_WEATHER_DAY, WeatherDay::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<WeatherDay>(EXTRA_WEATHER_DAY)
        }
        weatherDay?.let {
            displayWeatherDetails(it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.weather_detail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayWeatherDetails(weatherDay: WeatherDay) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val displayDateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru", "RU"))
        val date = try {
            val parsedDate = dateFormat.parse(weatherDay.date)
            if (parsedDate != null) {
                displayDateFormat.format(parsedDate)
            } else {
                weatherDay.date
            }
        } catch (e: Exception) {
            weatherDay.date
        }

        binding.dateText.text = date
        binding.weatherDescription.text = WeatherCodeMapper.getWeatherDescription(weatherDay.weatherCode)
        binding.temperatureText.text = getString(R.string.temperature, weatherDay.avgTemp.toInt())
        binding.minMaxTempText.text = getString(
            R.string.temperature,
            weatherDay.minTemp.toInt()
        ) + " / " + getString(R.string.temperature, weatherDay.maxTemp.toInt())

        binding.humidityText.text = getString(R.string.humidity, weatherDay.humidity)
        binding.windText.text = getString(
            R.string.wind_speed,
            String.format(Locale.getDefault(), "%.1f", weatherDay.windSpeed)
        )
        binding.pressureText.text = getString(
            R.string.pressure,
            String.format(Locale.getDefault(), "%.0f", weatherDay.pressure)
        )
        binding.precipitationText.text = getString(
            R.string.precipitation,
            String.format(Locale.getDefault(), "%.1f", weatherDay.precipitation)
        )

        binding.weatherIcon.setImageResource(WeatherCodeMapper.getWeatherIconRes(weatherDay.weatherCode))

        val backgroundColor = ContextCompat.getColor(
            this,
            WeatherCodeMapper.getBackgroundColorRes(weatherDay)
        )
        binding.mainCardContainer.setBackgroundColor(backgroundColor)
    }
}

