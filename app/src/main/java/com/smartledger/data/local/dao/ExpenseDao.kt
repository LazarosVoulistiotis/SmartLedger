package com.smartledger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smartledger.data.local.entity.ExpenseEntity

/*
  Data Access Object for locally stored expenses.

  The DAO defines the SQL operations Room will generate at compile time.
  Keeping these operations here prevents the repository or UI layer from
  containing raw database queries.
*/
@Dao
interface ExpenseDao {

    /*
      Inserts a single expense or replaces it if the same ID already exists.

      This is useful when the same expense is saved locally first and later
      updated after successful cloud synchronisation.
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExpense(expense: ExpenseEntity)

    /*
      Inserts or replaces multiple expenses.

      This supports refreshing the local cache after remote Firestore data
      has been loaded successfully.
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExpenses(expenses: List<ExpenseEntity>)

    /*
      Returns all locally saved expenses for the authenticated user.

      Results are ordered from newest to oldest so the History screen can show
      the most recent financial activity first.
    */
    @Query(
        """
        SELECT * FROM expenses
        WHERE ownerUid = :ownerUid
        ORDER BY createdAt DESC
        """
    )
    suspend fun getExpensesForUser(ownerUid: String): List<ExpenseEntity>

    /*
      Updates the sync flag after a Firestore save attempt.

      If the cloud save succeeds, isSynced becomes true. If the user is offline,
      the expense can remain available locally with isSynced set to false.
    */
    @Query("UPDATE expenses SET isSynced = :isSynced WHERE id = :expenseId")
    suspend fun updateSyncStatus(expenseId: String, isSynced: Boolean)
}