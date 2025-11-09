package com.example.weather.auth.data.repository

import com.example.weather.auth.data.local.dao.UserDao
import com.example.weather.auth.data.local.entity.UserEntity
import com.example.weather.auth.domain.model.User
import com.example.weather.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun registerUser(user: User): Result<Long> {
        return try {
            val existingUser = userDao.checkUserExists(user.email)
            if (existingUser != null) {
                Result.failure(Exception("Пользователь с таким email уже существует"))
            } else {
                val userEntity = UserEntity(
                    username = user.username,
                    email = user.email,
                    password = user.password
                )
                val userId = userDao.insertUser(userEntity)
                Result.success(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<User?> {
        return try {
            val userEntity = userDao.getUserByEmailAndPassword(email, password)
            if (userEntity != null) {
                val user = User(
                    id = userEntity.id,
                    username = userEntity.username,
                    email = userEntity.email,
                    password = userEntity.password
                )
                Result.success(user)
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            val userEntity = userDao.getUserByEmail(email)
            userEntity?.let {
                User(
                    id = it.id,
                    username = it.username,
                    email = it.email,
                    password = it.password
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}

