package com.example.weather.di

import com.example.weather.calculator.data.repository.CalculatorRepositoryImpl
import com.example.weather.calculator.domain.repository.CalculatorRepository
import com.example.weather.calculator.domain.use_case.CalculateUseCase
import com.example.weather.calculator.presentation.viewmodel.CalculatorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val calculatorModule = module {
    single<CalculatorRepository> {
        CalculatorRepositoryImpl()
    }

    single {
        CalculateUseCase(get())
    }

    viewModel {
        CalculatorViewModel(get())
    }
}

