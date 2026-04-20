package com.smartledger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.smartledger.data.model.Expense
import kotlinx.coroutines.tasks.await

// Repository για αποθήκευση και ανάκτηση εξόδων από το Firestore
class ExpenseRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Αποθηκεύει νέο προσωπικό έξοδο για τον τρέχοντα χρήστη
    suspend fun addExpense(
        title: String,
        amount: Double,
        category: String,
        date: String,
        description: String
    ): Result<Unit> = runCatching {
        val currentUser = auth.currentUser ?: error("No authenticated user found.")

        val docRef = firestore.collection("expenses").document()

        val expense = Expense(
            id = docRef.id,
            ownerUid = currentUser.uid,
            title = title,
            amount = amount,
            category = category,
            date = date,
            description = description,
            groupId = null,
            createdAt = System.currentTimeMillis()
        )

        docRef.set(expense).await()
    }

    // Επιστρέφει τα έξοδα του τρέχοντα χρήστη ταξινομημένα από το πιο πρόσφατο
    suspend fun getMyExpenses(): Result<List<Expense>> = runCatching {
        val currentUser = auth.currentUser ?: error("No authenticated user found.")

        val snapshot = firestore
            .collection("expenses")
            .whereEqualTo("ownerUid", currentUser.uid)
            .get()
            .await()

        snapshot.documents
            .mapNotNull { doc ->
                doc.toObject(Expense::class.java)?.copy(id = doc.id)
            }
            .sortedByDescending { it.createdAt }
    }
}