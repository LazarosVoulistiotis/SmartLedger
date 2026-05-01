package com.smartledger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  Room entity that represents an expense record stored locally on the device.

  This class is separate from the domain/data model Expense because Room needs
  a database-specific representation of the same financial record.
*/
@Entity(tableName = "expenses")
data class ExpenseEntity(

    /*
      The expense ID is used as the primary key so the same record can be
      safely updated or replaced when local and cloud data are synchronised.
    */
    @PrimaryKey
    val id: String,

    // Firebase UID of the user who owns this expense.
    val ownerUid: String,

    // Basic expense details shown in the Add Expense and History screens.
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val description: String,

    /*
      Optional group identifier. It remains nullable because personal expenses
      are not always connected to a group split.
    */
    val groupId: String?,

    // Timestamp used for ordering expenses from newest to oldest.
    val createdAt: Long,

    /*
      Tracks whether the local expense has also been saved to Firestore.
      This supports offline-first behaviour and is useful for viva explanation.
    */
    val isSynced: Boolean
)