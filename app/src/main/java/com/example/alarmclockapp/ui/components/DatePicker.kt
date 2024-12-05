package com.example.alarmclockapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Displays a Material3-compatible DatePicker, allowing users to select a date.
 *
 * @param onDateSelected A callback function that returns the date selected by the user
 *                       as a [LocalDate]. This is triggered when the user selects a date.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePicker(onDateSelected: (LocalDate) -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    DatePicker(
        state = datePickerState,
        modifier = Modifier.padding(8.dp)
    )

    val selectedDateMillis = datePickerState.selectedDateMillis
    selectedDateMillis?.let {
        val selectedDate =
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        onDateSelected(selectedDate)
    }
}
