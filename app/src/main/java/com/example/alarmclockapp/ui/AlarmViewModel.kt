package com.example.alarmclockapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * ViewModel for managing alarm logic and application state.
 * This class controls the setting and triggering of alarms based on the user's input.
 */
class AlarmViewModel : ViewModel() {

    /**
     * The current state of the alarm, represented by [AlarmUiState].
     * This state includes the selected date, time, and alarm status.
     */
    var alarmData by mutableStateOf(AlarmUiState())

    /**
     * Updates the selected date in the alarm state.
     * This is invoked when the user selects a date.
     *
     * @param date The date selected by the user as a [LocalDate].
     */
    fun onDateSelected(date: LocalDate) {
        alarmData = alarmData.copy(selectedDate = date)
    }

    /**
     * Updates the selected time in the alarm state.
     * This is invoked when the user selects a time.
     *
     * @param time The time selected by the user as a [LocalTime].
     */
    fun onTimeSelected(time: LocalTime) {
        alarmData = alarmData.copy(selectedTime = time)
    }

    /**
     * Starts a countdown that triggers the alarm when the current date and time
     * match or exceed the selected date and time.
     * - Checks the current time against the selected time every second.
     * - Stops when the alarm is triggered or canceled.
     */
    private fun startAlarmCountdown() {
        viewModelScope.launch {
            while (alarmData.isAlarmSet && !alarmData.isAlarmTriggered) {
                val currentDateTime = LocalDateTime.now()
                val alarmDateTime = LocalDateTime.of(alarmData.selectedDate, alarmData.selectedTime)

                if (currentDateTime.isEqual(alarmDateTime) || currentDateTime.isAfter(alarmDateTime)) {
                    alarmData = alarmData.copy(isAlarmTriggered = true, isAlarmSet = false)
                }
                delay(1000L) // Check every second
            }
        }
    }

    /**
     * Toggles the alarm state based on the current status and user input.
     * - If the alarm is set, it deactivates the alarm.
     * - If no date or time is selected, no action is taken.
     * - If a date and time are selected, it activates the alarm.
     */
    fun toggleAlarm() {
        if (alarmData.isAlarmSet) {
            alarmData = alarmData.copy(isAlarmSet = false, isAlarmTriggered = false)
        } else if (alarmData.selectedDate != null && alarmData.selectedTime != null) {
            alarmData = alarmData.copy(isAlarmSet = true, isAlarmTriggered = false)
            startAlarmCountdown()
        }
    }

    /**
     * Resets the alarm trigger state, typically after the notification that the
     * alarm time has been reached is displayed.
     */
    fun resetAlarmTrigger() {
        alarmData = alarmData.copy(isAlarmTriggered = false)
    }
}
