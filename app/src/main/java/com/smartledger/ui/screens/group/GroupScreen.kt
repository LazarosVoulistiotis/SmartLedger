package com.smartledger.ui.screens.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.smartledger.ui.viewmodel.GroupViewModel
import java.util.Locale

@Composable
fun GroupScreen(
    navController: NavController,
    groupViewModel: GroupViewModel
) {
    val uiState by groupViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Group / Split Demo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Create a demo group, enter comma-separated member names, and calculate an equal split.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.groupName,
            onValueChange = groupViewModel::onGroupNameChanged,
            label = { Text("Group Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.membersInput,
            onValueChange = groupViewModel::onMembersInputChanged,
            label = { Text("Members (e.g. Lazaros, John, Jason)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.totalAmount,
            onValueChange = groupViewModel::onTotalAmountChanged,
            label = { Text("Total Amount") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        uiState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        uiState.successMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { groupViewModel.createGroupAndSplit() },
            enabled = !uiState.isLoading
        ) {
            Text("Create Group & Calculate Split")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (
            uiState.createdGroupId != null &&
            uiState.calculatedShare != null &&
            uiState.resultTotalAmount != null &&
            uiState.resultMemberNames.isNotEmpty()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Latest Split Result",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Group: ${uiState.resultGroupName}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Total Amount: €${
                            String.format(
                                Locale.US,
                                "%.2f",
                                uiState.resultTotalAmount
                            )
                        }",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Share per Member: €${
                            String.format(
                                Locale.US,
                                "%.2f",
                                uiState.calculatedShare
                            )
                        }",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Group ID: ${uiState.createdGroupId}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Members",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    uiState.resultMemberNames.forEach { member ->
                        Text(
                            text = "• $member — €${
                                String.format(
                                    Locale.US,
                                    "%.2f",
                                    uiState.calculatedShare
                                )
                            }",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { navController.popBackStack() },
            enabled = !uiState.isLoading
        ) {
            Text("Back")
        }
    }
}