package com.example.segurosfacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.segurosfacil.navigation.NavGraph
import com.example.segurosfacil.ui.theme.theme.SegurosFacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SegurosFacilTheme {
                NavGraph()
            }
        }
    }
}