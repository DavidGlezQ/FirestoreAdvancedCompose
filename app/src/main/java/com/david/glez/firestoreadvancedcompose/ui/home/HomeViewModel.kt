package com.david.glez.firestoreadvancedcompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firestoreadvancedcompose.data.TransactionDto
import com.david.glez.firestoreadvancedcompose.data.network.DatabaseRepository
import com.david.glez.firestoreadvancedcompose.domain.model.TransactionModel
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val databaseRepository: DatabaseRepository): ViewModel() {


    private var _uiState = MutableStateFlow<HomeUIState>(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        viewModelScope.launch {
            databaseRepository.getTransactions().collect {transactions ->
                _uiState.update { uiState ->
                    uiState.copy(
                       transactions = transactions,
                        totalAmount = String.format("%.2f$", transactions.sumOf { it.amount })
                    )
                }
            }
        }
    }

    fun onAddTransactionSelected() {
        _uiState.update { it.copy(showAddTransaction = true) }
    }

    fun dismissAddDialog() {
        _uiState.update { it.copy(showAddTransaction = false) }
    }

    fun onAddTransaction(title: String, amount: String, date: Long?) {
        val dto = prepareDTO(title, amount, date)
        if(dto != null) {
            viewModelScope.launch {
                databaseRepository.addTransaction(dto)
            }
        }
        dismissAddDialog()
    }

    private fun prepareDTO(title: String, amount: String, date: Long?): TransactionDto? {
        if (title.isBlank() || amount.isBlank()) return null
        val timeStamp = if (date != null) {
            val seconds = date / 1000
            val nanoseconds = ((date % 1000) * 1000000).toInt()
            Timestamp(seconds, nanoseconds)
        } else {
            Timestamp.now()
        }
        return TransactionDto(
            title = title,
            amount = amount.toDouble(),
            date = timeStamp
        )
    }
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionModel> = emptyList(),
    val totalAmount: String = "",
    val showAddTransaction: Boolean = false

)