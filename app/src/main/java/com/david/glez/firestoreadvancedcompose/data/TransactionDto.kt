package com.david.glez.firestoreadvancedcompose.data

import com.google.firebase.Timestamp

data class TransactionDto(
    val title: String,
    val amount: Double,
    val date: Timestamp
)
