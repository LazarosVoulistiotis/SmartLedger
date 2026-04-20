package com.smartledger.data.model

// Μοντέλο δεδομένων για μία ομάδα διαμοιρασμού εξόδων
data class Group(
    val id: String = "",
    val name: String = "",
    val createdBy: String = "",
    val memberUids: List<String> = emptyList(),
    val memberNames: List<String> = emptyList(),
    val createdAt: Long = 0L
)