package com.david.glez.firestoreadvancedcompose.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransaction(onDismiss: () -> Unit, onTransactionAdded: (String, String, Long?) -> Unit) {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Dialog(onDismissRequest = { onDismiss() }) {

        if (showDatePicker) {
            DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
                TextButton(onClick = {
                    date = datePickerState.selectedDateMillis.millisToDate()
                    showDatePicker = false
                }) {
                    Text("Ok")
                }
            }, dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }) {
                DatePicker(state = datePickerState)
            }
        }

        Card() {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Add Transaction", fontSize = 24.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Concept") })
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextButton(onClick = { showDatePicker = true }) {
                    Text("Select Date: $date")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = {
                    onTransactionAdded(title, amount, datePickerState.selectedDateMillis)
                }) {
                    Text("Add Transaction")
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}