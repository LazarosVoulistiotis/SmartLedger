package com.smartledger.ui.viewmodel

import com.smartledger.data.model.Expense

/*
  Holds all UI state for expense-related screens.

  AddExpenseScreen uses the form fields and save messages.
  HistoryScreen uses the expenses list and localInfoMessage to explain when
  local Room data is being shown.
*/
data class ExpenseUiState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val localInfoMessage: String? = null
)