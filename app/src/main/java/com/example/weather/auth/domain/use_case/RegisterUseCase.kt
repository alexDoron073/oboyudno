package com.example.weather.auth.domain.use_case

import com.example.weather.auth.domain.model.User
import com.example.weather.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): Result<Long> {
        return repository.registerUser(user)
    }
}

