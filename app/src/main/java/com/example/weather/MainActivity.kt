package com.example.weather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.calculator.presentation.ui.CalculatorActivity
import com.example.weather.databinding.ActivityMainMenuBinding
import com.example.weather.weather.presentation.ui.WeatherActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupButtons()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.main_menu)
    }

    private fun setupButtons() {
        binding.weatherButton.setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        binding.calculatorButton.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }
    }
}
