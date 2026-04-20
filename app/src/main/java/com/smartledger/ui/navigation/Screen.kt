package com.smartledger.ui.navigation
// // Κεντρικός ορισμός των routes για όλες τις οθόνες της εφαρμογής
sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Dashboard : Screen("dashboard")
    data object AddExpense : Screen("add_expense")
    data object History : Screen("history")
    data object Group : Screen("group")
}
