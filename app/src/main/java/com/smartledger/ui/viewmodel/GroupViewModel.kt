package com.smartledger.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartledger.data.repository.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupViewModel(
    private val groupRepository: GroupRepository = GroupRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupUiState())
    val uiState: StateFlow<GroupUiState> = _uiState.asStateFlow()

    fun onGroupNameChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            groupName = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onMembersInputChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            membersInput = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun onTotalAmountChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            totalAmount = value,
            errorMessage = null,
            successMessage = null
        )
    }

    fun createGroupAndSplit() {
        val currentState = _uiState.value

        if (currentState.groupName.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a group name."
            )
            return
        }

        if (currentState.membersInput.isBlank()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter member names separated by commas."
            )
            return
        }

        val parsedMembers = currentState.membersInput
            .split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        if (parsedMembers.isEmpty()) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter at least one valid member name."
            )
            return
        }

        val totalAmountValue = currentState.totalAmount.toDoubleOrNull()
        if (totalAmountValue == null || totalAmountValue <= 0.0) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a valid total amount."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            val result = groupRepository.createGroupAndSplit(
                groupName = currentState.groupName.trim(),
                membersInput = currentState.membersInput,
                totalAmount = totalAmountValue
            )

            _uiState.value = if (result.isSuccess) {
                val (groupId, share) = result.getOrThrow()

                _uiState.value.copy(
                    groupName = "",
                    membersInput = "",
                    totalAmount = "",
                    isLoading = false,
                    errorMessage = null,
                    successMessage = "Group and split created successfully.",
                    calculatedShare = share,
                    createdGroupId = groupId,
                    resultGroupName = currentState.groupName.trim(),
                    resultMemberNames = parsedMembers,
                    resultTotalAmount = totalAmountValue
                )
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                        ?: "Failed to create group/split.",
                    successMessage = null
                )
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}