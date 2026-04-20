package com.smartledger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartledger.ui.screens.auth.LoginScreen
import com.smartledger.ui.screens.auth.RegisterScreen
import com.smartledger.ui.screens.auth.WelcomeScreen
import com.smartledger.ui.screens.dashboard.DashboardScreen
import com.smartledger.ui.screens.expense.AddExpenseScreen
import com.smartledger.ui.screens.group.GroupScreen
import com.smartledger.ui.screens.history.HistoryScreen
import com.smartledger.ui.viewmodel.AuthViewModel
import com.smartledger.ui.viewmodel.ExpenseViewModel
import com.smartledger.ui.viewmodel.GroupViewModel

@Composable
fun SmartLedgerNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()
    val groupViewModel: GroupViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController, authViewModel)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController, authViewModel)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController, authViewModel)
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(navController, expenseViewModel)
        }

        composable(Screen.History.route) {
            HistoryScreen(navController, expenseViewModel)
        }

        composable(Screen.Group.route) {
            GroupScreen(navController, groupViewModel)
        }
    }
}