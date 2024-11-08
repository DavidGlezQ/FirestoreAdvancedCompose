package com.david.glez.firestoreadvancedcompose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.glez.firestoreadvancedcompose.R
import com.david.glez.firestoreadvancedcompose.domain.model.TransactionModel
import com.david.glez.firestoreadvancedcompose.ui.theme.Grey
import com.david.glez.firestoreadvancedcompose.ui.theme.Purple40
import com.david.glez.firestoreadvancedcompose.ui.theme.Purple80

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val uiState: HomeUIState by homeViewModel.uiState.collectAsState()

    Column {
        Text(
            "Hello David",
            fontSize = 24.sp,
            color = Grey,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp)
        )
        Balance(isLoading = uiState.isLoading, totalAmount = uiState.totalAmount)
        Text(
            text = "Transacciones recientes",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Transactions(transactions = uiState.transactions)
    }
}

@Composable
fun Transactions(transactions: List<TransactionModel>) {
    LazyColumn {
        items(transactions) {
            TransactionItem(transactionModel = it)
        }
    }
}

@Composable
fun TransactionItem(transactionModel: TransactionModel) {
    Row(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_money), contentDescription = null)
        Spacer(modifier = Modifier.padding(24.dp))
        Column {
            Text(text = transactionModel.title, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = transactionModel.date, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = String.format("%.2f$", transactionModel.amount),
            color = Color.Red,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Balance(isLoading: Boolean, totalAmount: String) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(Purple80, Purple40)))
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Debes", color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    if (!isLoading) {
                        Text(
                            totalAmount,
                            fontSize = 28.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        CircularProgressIndicator()
                    }

                }

                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "", modifier = Modifier.size(44.dp)
                    )
                }
            }
        }
    }
}