package com.smartledger.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.smartledger.data.model.Expense
import com.smartledger.ui.viewmodel.ExpenseViewModel
import java.util.Locale

@Composable
fun HistoryScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel
) {
    val uiState by expenseViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        expenseViewModel.loadMyExpenses()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Expense History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (uiState.expenses.isEmpty() && !uiState.isLoading) {
            Text(
                text = "No expenses found yet.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f, fill = false)
                .fillMaxWidth()
        ) {
            items(uiState.expenses, key = { it.id }) { expense ->
                ExpenseCard(expense = expense)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { expenseViewModel.loadMyExpenses() }) {
            Text("Refresh")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

@Composable
private fun ExpenseCard(expense: Expense) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Amount: €${String.format(Locale.US, "%.2f", expense.amount)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Category: ${expense.category}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Date: ${expense.date}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (expense.description.isNotBlank()) {
                Text(
                    text = "Description: ${expense.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}