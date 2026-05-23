package com.example.lab_04_00073824

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lab_04_00073824.navigation.AppNavigation
import com.example.lab_04_00073824.ui.theme.Lab_04_00073824Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_04_00073824Theme {
                AppNavigation()
            }
        }
    }
}