package com.example.alarmclockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.alarmclockapp.ui.AlarmScreen
import com.example.alarmclockapp.ui.theme.AlarmClockAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmClockAppTheme {
              AlarmScreen()
            }
        }
    }
}