package com.smartledger.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smartledger.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
  ViewModel for expense-related UI state and actions.

  It extends AndroidViewModel because the ExpenseRepository now needs an
  application Context to initialise the Room database safely.
*/
class ExpenseViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val expenseRepository = ExpenseRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    fun onTitleChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            title = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onAmountChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            amount = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onCategoryChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            category = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onDateChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            date = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onDescriptionChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            description = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun addExpense() {
        val currentState = _uiState.value

        if (currentState.title.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Please enter a title.")
            return
        }

        val amountValue = currentState.amount.toDoubleOrNull()
        if (amountValue == null || amountValue <= 0.0) {
            _uiState.value = currentState.copy(errorMessage = "Please enter a valid amount.")
            return
        }

        if (currentState.category.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Please enter a category.")
            return
        }

        if (currentState.date.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Please enter a date.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            val result = expenseRepository.addExpense(
                title = currentState.title.trim(),
                amount = amountValue,
                category = currentState.category.trim(),
                date = currentState.date.trim(),
                description = currentState.description.trim()
            )

            _uiState.value = if (result.isSuccess) {
                val syncedToCloud = result.getOrDefault(false)

                _uiState.value.copy(
                    title = "",
                    amount = "",
                    category = "",
                    date = "",
                    description = "",
                    isLoading = false,
                    errorMessage = null,
                    successMessage = if (syncedToCloud) {
                        "Expense saved locally and synced to cloud."
                    } else {
                        "Expense saved locally. Sync will be attempted when online."
                    }
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to save expense.",
                    successMessage = null
                )
            }
        }
    }

    fun loadMyExpenses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                localInfoMessage = null
            )

            val result = expenseRepository.getMyExpenses()

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(
                    isLoading = false,
                    expenses = result.getOrDefault(emptyList()),
                    errorMessage = null,
                    localInfoMessage = "Showing saved expenses from local storage when cloud data is unavailable."
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to load expenses."
                )
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null,
            localInfoMessage = null
        )
    }
}