package com.example.covid.ui.navigation

sealed class Screen(val route: String) {
    object CovidScreen : Screen("covidScreen")
    object InfoScreen : Screen("infoScreen")
}
