package com.smartledger.ui.viewmodel

import com.smartledger.data.model.Expense

data class ExpenseUiState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)