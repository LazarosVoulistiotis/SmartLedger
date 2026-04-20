package com.smartledger.ui.viewmodel

data class GroupUiState(
    val groupName: String = "",
    val membersInput: String = "",
    val totalAmount: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val calculatedShare: Double? = null,
    val createdGroupId: String? = null,

    // Result panel data για πιο καθαρή παρουσίαση στο UI
    val resultGroupName: String = "",
    val resultMemberNames: List<String> = emptyList(),
    val resultTotalAmount: Double? = null
)