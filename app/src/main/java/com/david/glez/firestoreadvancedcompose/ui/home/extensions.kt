package com.david.glez.firestoreadvancedcompose.ui.home

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long?.millisToDate(): String {
    this ?: return ""
    return try {
        val date = Date(this)
        val format = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        ""
    }

}