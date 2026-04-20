package com.smartledger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.smartledger.data.model.Group
import com.smartledger.data.model.SplitRecord
import com.smartledger.domain.usecase.SplitCalculationUseCase
import kotlinx.coroutines.tasks.await

class GroupRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val splitCalculationUseCase: SplitCalculationUseCase = SplitCalculationUseCase()
) {

    suspend fun createGroupAndSplit(
        groupName: String,
        membersInput: String,
        totalAmount: Double
    ): Result<Pair<String, Double>> = runCatching {
        val currentUser = auth.currentUser ?: error("No authenticated user found.")

        val memberNames = membersInput
            .split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        if (memberNames.isEmpty()) {
            error("Please enter at least one member name.")
        }

        val groupRef = firestore.collection("groups").document()

        val group = Group(
            id = groupRef.id,
            name = groupName,
            createdBy = currentUser.uid,
            memberUids = listOf(currentUser.uid),
            memberNames = memberNames,
            createdAt = System.currentTimeMillis()
        )

        groupRef.set(group).await()

        val sharePerMember = splitCalculationUseCase(
            totalAmount = totalAmount,
            membersCount = memberNames.size
        )

        val splitRef = firestore.collection("splits").document()

        val splitRecord = SplitRecord(
            id = splitRef.id,
            expenseId = "",
            groupId = groupRef.id,
            payerUid = currentUser.uid,
            participantUids = listOf(currentUser.uid),
            participantNames = memberNames,
            sharePerMember = sharePerMember,
            totalAmount = totalAmount,
            createdAt = System.currentTimeMillis()
        )

        splitRef.set(splitRecord).await()

        groupRef.id to sharePerMember
    }
}