package com.david.glez.firestoreadvancedcompose.domain.model

data class TransactionModel(
    val id: String,
    val title: String,
    val amount: Double,
    val date: String
)
