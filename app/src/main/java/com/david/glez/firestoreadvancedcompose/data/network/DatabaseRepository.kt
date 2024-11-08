package com.david.glez.firestoreadvancedcompose.data.network

import com.david.glez.firestoreadvancedcompose.data.TransactionDto
import com.david.glez.firestoreadvancedcompose.data.network.response.TransactionResponse
import com.david.glez.firestoreadvancedcompose.domain.model.TransactionModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val database: FirebaseFirestore) {

    companion object {
        const val TRANSACTIONS = "transactions"
        const val FIELD_DATE = "date"

    }

    fun getTransactions(): Flow<List<TransactionModel>> {
        return database.collection(TRANSACTIONS).orderBy(FIELD_DATE, Query.Direction.DESCENDING)
            .snapshots().map {
                it.toObjects(TransactionResponse::class.java).mapNotNull { transactionResponse ->
                    transactionToDomain(transactionResponse = transactionResponse)
                }
            }
    }

    private fun transactionToDomain(transactionResponse: TransactionResponse): TransactionModel? {
        if (transactionResponse.date == null || transactionResponse.amount == null || transactionResponse.id == null || transactionResponse.title == null) return null
        val date = timestampToString(transactionResponse.date) ?: return null
        return TransactionModel(
            id = transactionResponse.id,
            title = transactionResponse.title,
            amount = transactionResponse.amount,
            date = date
        )
    }

    private fun timestampToString(timestamp: Timestamp?): String? {
        timestamp ?: return null
        return try {
            val date = timestamp.toDate()
            val format = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
            format.format(date)
        } catch (e: Exception) {
            null
        }
    }

    fun addTransaction(dto: TransactionDto) {
        val customId = getCustomId()
        val model = hashMapOf(
            "id" to customId,
            "title" to dto.title,
            "date" to dto.date,
            "amount" to dto.amount
        )
        database.collection(TRANSACTIONS).document(customId).set(model)
    }

    private fun getCustomId(): String {
        return Date().time.toString()
    }

    fun removeTransaction(id: String) {
        database.collection(TRANSACTIONS).document(id).delete()
    }
}