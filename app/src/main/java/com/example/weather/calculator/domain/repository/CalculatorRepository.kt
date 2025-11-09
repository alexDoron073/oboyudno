package com.example.weather.calculator.domain.repository

interface CalculatorRepository {
    fun calculate(expression: String): Result<Double>
}

