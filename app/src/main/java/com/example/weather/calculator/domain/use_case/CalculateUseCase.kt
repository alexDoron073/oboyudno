package com.example.weather.calculator.domain.use_case

import com.example.weather.calculator.domain.repository.CalculatorRepository

class CalculateUseCase(
    private val repository: CalculatorRepository
) {
    operator fun invoke(expression: String): Result<Double> {
        return repository.calculate(expression)
    }
}

