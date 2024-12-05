package com.example.alarmclockapp.ui

import java.time.LocalDate
import java.time.LocalTime

data class AlarmUiState(
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val isAlarmSet: Boolean = false,
    val isAlarmTriggered: Boolean = false
)
