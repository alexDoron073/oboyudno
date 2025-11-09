package com.example.weather.auth.domain.repository

import com.example.weather.auth.domain.model.User

interface AuthRepository {
    suspend fun registerUser(user: User): Result<Long>
    suspend fun loginUser(email: String, password: String): Result<User?>
    suspend fun getUserByEmail(email: String): User?
}

