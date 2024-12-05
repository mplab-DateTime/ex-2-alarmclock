package com.example.alarmclockapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import java.util.Calendar

/**
 * Displays a Material3-compatible TimePicker dialog that allows users to select a time.
 *
 * @param onConfirm A callback function triggered when the user confirms their selection.
 *                  Provides the selected hour and minute as integers.
 * @param onDismiss A callback function triggered when the dialog is dismissed without a selection.
 *
 * This function uses the current system time as the default initial value in the TimePicker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePickerDialog(
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(timePickerState.hour, timePickerState.minute)
        }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

/**
 * A reusable dialog component for displaying a TimePicker with confirm and dismiss actions.
 *
 * @param onDismiss A callback function triggered when the dialog is dismissed.
 * @param onConfirm A callback function triggered when the user confirms their selection.
 * @param content   A composable lambda that defines the content of the dialog. This is
 *                  typically where the TimePicker is placed.
 */
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}
