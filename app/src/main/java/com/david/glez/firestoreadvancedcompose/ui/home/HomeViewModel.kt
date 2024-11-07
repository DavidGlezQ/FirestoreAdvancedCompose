package com.david.glez.firestoreadvancedcompose.ui.home

import androidx.lifecycle.ViewModel
import com.david.glez.firestoreadvancedcompose.domain.model.TransactionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private var _uiState = MutableStateFlow<HomeUIState>(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState
}

data class HomeUIState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionModel> = emptyList(),
    val totalAmount: String = ""

)