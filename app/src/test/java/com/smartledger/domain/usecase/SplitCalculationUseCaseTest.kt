package com.smartledger.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

/*
  Unit tests for the equal split business logic.

  These tests run independently from the Android UI and prove that the core
  calculation used in the group split feature behaves correctly.
*/
class SplitCalculationUseCaseTest {

    private val splitCalculationUseCase = SplitCalculationUseCase()

    @Test
    fun `total amount 100 split by 4 members returns 25`() {
        val result = splitCalculationUseCase(
            totalAmount = 100.0,
            membersCount = 4
        )

        assertEquals(25.0, result, 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `zero members throws IllegalArgumentException`() {
        splitCalculationUseCase(
            totalAmount = 100.0,
            membersCount = 0
        )
    }
}