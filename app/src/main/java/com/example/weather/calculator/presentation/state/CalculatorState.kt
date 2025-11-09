package com.example.weather.calculator.presentation.state

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)

