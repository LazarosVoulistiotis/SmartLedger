package com.smartledger.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.smartledger.ui.navigation.Screen
import com.smartledger.ui.viewmodel.AuthViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (uiState.email.isNotBlank()) "Signed in as: ${uiState.email}" else "Authenticated user session active",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(Screen.AddExpense.route) }) {
            Text("Add Expense")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate(Screen.History.route) }) {
            Text("Expense History")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate(Screen.Group.route) }) {
            Text("Group / Split")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                authViewModel.logout()
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Logout")
        }
    }
}