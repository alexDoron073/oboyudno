package com.example.weather.di

import com.example.weather.auth.data.local.dao.UserDao
import com.example.weather.auth.data.repository.AuthRepositoryImpl
import com.example.weather.auth.domain.repository.AuthRepository
import com.example.weather.auth.domain.use_case.LoginUseCase
import com.example.weather.auth.domain.use_case.RegisterUseCase
import com.example.weather.auth.presentation.viewmodel.LoginViewModel
import com.example.weather.auth.presentation.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get<UserDao>())
    }

    single {
        RegisterUseCase(get())
    }

    single {
        LoginUseCase(get())
    }

    viewModel {
        RegisterViewModel(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}

