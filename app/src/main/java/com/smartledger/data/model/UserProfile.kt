package com.smartledger.data.model

// Μοντέλο δεδομένων για το προφίλ του χρήστη
data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val createdAt: Long = 0L
)