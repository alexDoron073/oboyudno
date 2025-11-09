package com.example.weather.calculator.data.repository

import com.example.weather.calculator.domain.repository.CalculatorRepository
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorRepositoryImpl : CalculatorRepository {
    
    override fun calculate(expression: String): Result<Double> {
        return try {
            if (expression.isBlank()) {
                return Result.failure(IllegalArgumentException("Выражение не может быть пустым"))
            }
            
            val cleanExpression = expression.replace(" ", "")
            
            val result = ExpressionBuilder(cleanExpression)
                .build()
                .evaluate()
            
            if (result.isNaN() || result.isInfinite()) {
                Result.failure(IllegalArgumentException("Некорректный результат вычисления"))
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

