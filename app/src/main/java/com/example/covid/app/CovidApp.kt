package com.example.covid.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.covid.ui.navigation.CovidAppRouter
import com.example.covid.ui.navigation.Screen
import com.example.covid.ui.screens.InfoScreen
import com.example.covid.ui.screens.StatScreen

@Composable
fun CovidApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = CovidAppRouter.currentScreen, label = "") { currentState ->
            when (currentState.value) {
                is Screen.StatScreen -> StatScreen()
                is Screen.InfoScreen -> InfoScreen()

                else -> {}
            }
        }
    }
}