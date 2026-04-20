package com.smartledger.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartledger.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            isLoggedIn = authRepository.getCurrentUser() != null
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            errorMessage = null
        )
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            errorMessage = null
        )
    }

    fun onDisplayNameChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            displayName = value,
            errorMessage = null
        )
    }

    fun checkAuthState() {
        _uiState.value = _uiState.value.copy(
            isLoggedIn = authRepository.getCurrentUser() != null
        )
    }

    fun register() {
        val currentState = _uiState.value

        if (currentState.displayName.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a display name."
            )
            return
        }

        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter an email."
            )
            return
        }

        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a password."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = authRepository.register(
                email = _uiState.value.email.trim(),
                password = _uiState.value.password,
                displayName = _uiState.value.displayName.trim()
            )

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    password = "",
                    errorMessage = null
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Registration failed."
                )
            }
        }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter an email."
            )
            return
        }

        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a password."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val result = authRepository.login(
                email = _uiState.value.email.trim(),
                password = _uiState.value.password
            )

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    password = "",
                    errorMessage = null
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Login failed."
                )
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = _uiState.value.copy(
            isLoggedIn = false,
            password = ""
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}