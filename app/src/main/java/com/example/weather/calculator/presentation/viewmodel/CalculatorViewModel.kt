package com.example.weather.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weather.calculator.domain.use_case.CalculateUseCase
import com.example.weather.calculator.presentation.state.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel(
    private val calculateUseCase: CalculateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    fun appendToExpression(value: String) {
        _state.value = _state.value.copy(
            expression = _state.value.expression + value,
            error = null,
            result = ""
        )
    }

    fun clear() {
        _state.value = CalculatorState()
    }

    fun deleteLast() {
        val currentExpression = _state.value.expression
        if (currentExpression.isNotEmpty()) {
            _state.value = _state.value.copy(
                expression = currentExpression.dropLast(1),
                error = null,
                result = ""
            )
        }
    }

    fun calculate() {
        val expression = _state.value.expression
        if (expression.isBlank()) {
            return
        }

        _state.value = _state.value.copy(isLoading = true, error = null)
        
        calculateUseCase(expression)
            .onSuccess { result ->
                val formattedResult = if (result % 1.0 == 0.0) {
                    result.toInt().toString()
                } else {
                    String.format("%.10f", result).trimEnd('0').trimEnd('.')
                }
                _state.value = _state.value.copy(
                    result = formattedResult,
                    expression = formattedResult,
                    isLoading = false,
                    error = null
                )
            }
            .onFailure { exception ->
                _state.value = _state.value.copy(
                    error = exception.message ?: "Ошибка вычисления",
                    isLoading = false,
                    result = ""
                )
            }
    }
}

