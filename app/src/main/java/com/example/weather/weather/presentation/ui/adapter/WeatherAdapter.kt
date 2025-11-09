package com.example.weather.weather.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.ItemWeatherCardBinding
import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.presentation.util.WeatherCodeMapper
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherAdapter(
    private val onItemClick: (WeatherDay) -> Unit
) : ListAdapter<WeatherDay, WeatherAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class WeatherViewHolder(
        private val binding: ItemWeatherCardBinding,
        private val onItemClick: (WeatherDay) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherDay: WeatherDay, position: Int) {
            val context = binding.root.context

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

            val dayLabel = when (position) {
                0 -> context.getString(R.string.today) + ", "
                1 -> context.getString(R.string.tomorrow) + ", "
                else -> ""
            }

            binding.dateText.text = dayLabel + date
            binding.weatherDescription.text = WeatherCodeMapper.getWeatherDescription(weatherDay.weatherCode)
            binding.temperatureText.text = context.getString(R.string.temperature, weatherDay.avgTemp.toInt())
            binding.minMaxTempText.text = context.getString(
                R.string.temperature,
                weatherDay.minTemp.toInt()
            ) + " / " + context.getString(R.string.temperature, weatherDay.maxTemp.toInt())

            if (weatherDay.precipitation > 0) {
                binding.precipitationText.text = context.getString(
                    R.string.precipitation,
                    String.format(Locale.getDefault(), "%.1f", weatherDay.precipitation)
                )
                binding.precipitationText.visibility = android.view.View.VISIBLE
            } else {
                binding.precipitationText.visibility = android.view.View.GONE
            }

            binding.windText.text = context.getString(
                R.string.wind_speed,
                String.format(Locale.getDefault(), "%.1f", weatherDay.windSpeed)
            )

            binding.weatherIcon.setImageResource(WeatherCodeMapper.getWeatherIconRes(weatherDay.weatherCode))

            val backgroundColor = ContextCompat.getColor(
                context,
                WeatherCodeMapper.getBackgroundColorRes(weatherDay)
            )
            binding.cardContainer.setBackgroundColor(backgroundColor)

            binding.root.setOnClickListener {
                onItemClick(weatherDay)
            }
        }
    }

    class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherDay>() {
        override fun areItemsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem == newItem
        }
    }
}

