package com.example.weather.auth.domain.use_case

import com.example.weather.auth.domain.model.User
import com.example.weather.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User?> {
        return repository.loginUser(email, password)
    }
}

