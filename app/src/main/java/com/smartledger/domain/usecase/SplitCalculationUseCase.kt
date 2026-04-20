package com.smartledger.domain.usecase

class SplitCalculationUseCase {
    operator fun invoke(totalAmount: Double, membersCount: Int): Double {
        require(membersCount > 0) { "Members count must be greater than zero." }
        return totalAmount / membersCount
    }
}