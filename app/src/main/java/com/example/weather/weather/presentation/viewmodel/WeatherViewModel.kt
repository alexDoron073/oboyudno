package com.example.weather.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.weather.domain.model.WeatherDay
import com.example.weather.weather.domain.use_case.GetWeatherForecastUseCase
import com.example.weather.weather.presentation.state.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    private val moscowLatitude = 55.7558
    private val moscowLongitude = 37.6173

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            getWeatherForecastUseCase(moscowLatitude, moscowLongitude)
                .onSuccess { weatherDays ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        weatherDays = weatherDays,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Ошибка загрузки данных"
                    )
                }
        }
    }
}

