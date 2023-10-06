package com.example.covid.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object StatScreen : Screen()
    object InfoScreen : Screen()
}

object CovidAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.StatScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
