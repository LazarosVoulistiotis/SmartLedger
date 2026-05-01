package com.smartledger.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.smartledger.data.local.SmartLedgerDatabase
import com.smartledger.data.local.mapper.toEntity
import com.smartledger.data.local.mapper.toModel
import com.smartledger.data.model.Expense
import kotlinx.coroutines.tasks.await
import java.util.UUID

/*
  Repository responsible for expense persistence.

  It now coordinates both local storage through Room and remote storage through
  Cloud Firestore. The UI and ViewModel only talk to this repository, so they do
  not need to know where the data is stored.
*/
class ExpenseRepository(
    context: Context,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val expenseDao = SmartLedgerDatabase
        .getInstance(context)
        .expenseDao()

    /*
      Adds a new expense using an offline-first approach.

      1. Create the Expense model.
      2. Save it locally in Room immediately.
      3. Try to sync the same record to Firestore.
      4. Return true if cloud sync succeeds, or false if only local save succeeds.
    */
    suspend fun addExpense(
        title: String,
        amount: Double,
        category: String,
        date: String,
        description: String
    ): Result<Boolean> = runCatching {
        val currentUser = auth.currentUser ?: error("No authenticated user found.")

        val expenseId = UUID.randomUUID().toString()

        val expense = Expense(
            id = expenseId,
            ownerUid = currentUser.uid,
            title = title,
            amount = amount,
            category = category,
            date = date,
            description = description,
            groupId = null,
            createdAt = System.currentTimeMillis()
        )

        // Local save happens first, so the record remains available offline.
        expenseDao.upsertExpense(expense.toEntity(isSynced = false))

        return@runCatching try {
            firestore
                .collection("expenses")
                .document(expenseId)
                .set(expense)
                .await()

            // Mark the local copy as synced after Firestore confirms the save.
            expenseDao.updateSyncStatus(expenseId, isSynced = true)
            true
        } catch (_: Exception) {
            /*
              The local save has already succeeded. Returning false allows the
              ViewModel to show an offline/local-save message instead of failing.
            */
            false
        }
    }

    /*
      Loads expenses for the current user.

      The method first reads the Room cache, then attempts to refresh from
      Firestore. If the cloud request fails, the locally saved expenses are
      returned so the History screen still works offline.
    */
    suspend fun getMyExpenses(): Result<List<Expense>> = runCatching {
        val currentUser = auth.currentUser ?: error("No authenticated user found.")

        val localExpenses = expenseDao
            .getExpensesForUser(currentUser.uid)
            .map { it.toModel() }

        try {
            val snapshot = firestore
                .collection("expenses")
                .whereEqualTo("ownerUid", currentUser.uid)
                .get()
                .await()

            val remoteExpenses = snapshot.documents
                .mapNotNull { doc ->
                    doc.toObject(Expense::class.java)?.copy(id = doc.id)
                }
                .sortedByDescending { it.createdAt }

            // Keep Room updated with the latest cloud records.
            expenseDao.upsertExpenses(
                remoteExpenses.map { it.toEntity(isSynced = true) }
            )

            remoteExpenses
        } catch (_: Exception) {
            localExpenses
        }
    }
}