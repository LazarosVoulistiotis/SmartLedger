package com.smartledger.data.local.mapper

import com.smartledger.data.local.entity.ExpenseEntity
import com.smartledger.data.model.Expense

/*
  Converts the main Expense model into a Room ExpenseEntity.

  The repository uses this when it needs to save an expense locally.
  The isSynced flag is passed separately because sync status belongs to the
  local persistence layer, not to the main cloud/domain model.
*/
fun Expense.toEntity(isSynced: Boolean): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        ownerUid = ownerUid,
        title = title,
        amount = amount,
        category = category,
        date = date,
        description = description,
        groupId = groupId,
        createdAt = createdAt,
        isSynced = isSynced
    )
}

/*
  Converts a Room ExpenseEntity back into the main Expense model.

  The UI and ViewModel continue to work with Expense objects, so they do not
  need to know whether the data came from Room or Firestore.
*/
fun ExpenseEntity.toModel(): Expense {
    return Expense(
        id = id,
        ownerUid = ownerUid,
        title = title,
        amount = amount,
        category = category,
        date = date,
        description = description,
        groupId = groupId,
        createdAt = createdAt
    )
}