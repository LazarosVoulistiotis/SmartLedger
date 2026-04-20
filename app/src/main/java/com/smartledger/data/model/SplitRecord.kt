package com.smartledger.data.model

// Μοντέλο δεδομένων για την καταγραφή διαμοιρασμού ενός ομαδικού εξόδου
data class SplitRecord(
    val id: String = "",
    val expenseId: String = "",
    val groupId: String = "",
    val payerUid: String = "",
    val participantUids: List<String> = emptyList(),
    val participantNames: List<String> = emptyList(),
    val sharePerMember: Double = 0.0,
    val totalAmount: Double = 0.0,
    val createdAt: Long = 0L
)