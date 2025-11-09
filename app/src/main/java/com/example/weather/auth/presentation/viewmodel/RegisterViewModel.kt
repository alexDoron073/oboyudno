package com.example.weather.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.auth.domain.model.User
import com.example.weather.auth.domain.use_case.RegisterUseCase
import com.example.weather.auth.presentation.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _state.value = _state.value.copy(error = "Заполните все поля")
            return
        }

        if (password != confirmPassword) {
            _state.value = _state.value.copy(error = "Пароли не совпадают")
            return
        }

        if (password.length < 6) {
            _state.value = _state.value.copy(error = "Пароль должен содержать минимум 6 символов")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            val user = User(
                username = username,
                email = email,
                password = password
            )
            
            registerUseCase(user)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Ошибка регистрации"
                    )
                }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

