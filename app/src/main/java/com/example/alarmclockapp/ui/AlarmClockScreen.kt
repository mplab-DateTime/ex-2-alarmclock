package com.example.alarmclockapp.ui

import com.example.alarmclockapp.ui.components.ShowDatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmclockapp.R
import com.example.alarmclockapp.ui.components.ShowTimePickerDialog
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(viewModel: AlarmViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val alarmData = viewModel.alarmData
    var showTimePickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.logohhn),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(128.dp)
                                .padding(end = 16.dp)
                        )

                        Text(
                            "Alarm Clock App",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )
        }


    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectedDateTimeDisplay(
                selectedDate = alarmData.selectedDate?.formatDate(),
                selectedTime = alarmData.selectedTime?.formatTime()
            )

            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            ShowDatePicker(
                onDateSelected = { viewModel.onDateSelected(it) }
            )

            TimeSelectionButton(
                onTimeClick = { showTimePickerDialog = true }
            )

            if (showTimePickerDialog) {
                ShowTimePickerDialog(
                    onConfirm = { hour, minute ->
                        viewModel.onTimeSelected(LocalTime.of(hour, minute))
                        showTimePickerDialog = false
                    },
                    onDismiss = { showTimePickerDialog = false }
                )
            }

            AlarmToggleButton(
                isAlarmSet = alarmData.isAlarmSet,
                onToggleAlarm = viewModel::toggleAlarm
            )

            if (alarmData.isAlarmTriggered) {
                AlarmTriggeredDialog(onDismiss = viewModel::resetAlarmTrigger)
            }
        }
    }
}

@Composable
private fun SelectedDateTimeDisplay(selectedDate: String?, selectedTime: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = selectedDate ?: "No date selected",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = selectedTime ?: "No time selected",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun TimeSelectionButton(onTimeClick: () -> Unit) {
    Button(
        onClick = onTimeClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Select time")
    }
}

@Composable
private fun AlarmToggleButton(isAlarmSet: Boolean, onToggleAlarm: () -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onToggleAlarm,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = if (isAlarmSet) "Stop alarm" else "Start alarm")
    }
}

@Composable
private fun AlarmTriggeredDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Alarm") },
        text = { Text("The selected time has been reached!") },
        confirmButton = {
            Button(onClick = onDismiss) { Text("OK") }
        }
    )
}

private fun LocalDate.formatDate(): String = this.format(
    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.getDefault())
)

private fun LocalTime.formatTime(): String = this.format(
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
)
