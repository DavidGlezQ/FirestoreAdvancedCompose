package com.david.glez.firestoreadvancedcompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.glez.firestoreadvancedcompose.data.network.DatabaseRepository
import com.david.glez.firestoreadvancedcompose.domain.model.TransactionModel
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
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionModel> = emptyList(),
    val totalAmount: String = ""

)