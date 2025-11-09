package com.example.weather.weather.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.weather.presentation.ui.adapter.WeatherAdapter
import com.example.weather.weather.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter
    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.weather_forecast)
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter { weatherDay ->
            val intent = Intent(this, WeatherDetailActivity::class.java)
            intent.putExtra(WeatherDetailActivity.EXTRA_WEATHER_DAY, weatherDay)
            startActivity(intent)
        }
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.weatherRecyclerView.adapter = weatherAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                binding.weatherRecyclerView.visibility = if (state.weatherDays.isNotEmpty()) View.VISIBLE else View.GONE
                
                if (state.weatherDays.isNotEmpty()) {
                    weatherAdapter.submitList(state.weatherDays)
                }
                
                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.errorText.text = "${getString(R.string.error_loading)}: ${state.error}"
                } else {
                    binding.errorText.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                }
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadWeatherData()
        }
    }
}

