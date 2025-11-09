package com.example.weather.weather.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherDetailBinding
import com.example.weather.weather.presentation.util.WeatherCodeMapper
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherDetailFragment : Fragment() {
    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private val args: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        displayWeatherDetails(args.weatherDay)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun displayWeatherDetails(weatherDay: com.example.weather.weather.domain.model.WeatherDay) {
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
            requireContext(),
            WeatherCodeMapper.getBackgroundColorRes(weatherDay)
        )
        binding.mainCardContainer.setBackgroundColor(backgroundColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

