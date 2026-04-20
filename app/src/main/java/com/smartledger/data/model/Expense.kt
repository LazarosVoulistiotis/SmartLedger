package com.smartledger.data.model

// Μοντέλο δεδομένων για μία εγγραφή εξόδου
data class Expense(
    val id: String = "",
    val ownerUid: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val groupId: String? = null,
    val createdAt: Long = 0L
)