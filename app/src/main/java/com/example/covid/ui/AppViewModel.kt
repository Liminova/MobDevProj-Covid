package com.example.covid.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid.ui.functions.generateRandomDataPoints
import com.example.covid.ui.sections.GraphUiState
import com.example.covid.ui.sections.SuccessData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    var graphUiState: GraphUiState by mutableStateOf(GraphUiState.Loading)
        private set
    var lastUpdated = mutableStateOf("loading...")
        private set
    var totalCases = mutableIntStateOf(0)
        private set
    var totalDeaths = mutableIntStateOf(0)
        private set
    var selectedCountry = mutableStateOf("Select a country")
        private set
    var scrollState = ScrollState(0)
    var topBarTitle = mutableStateOf("")

    init {
        graphUiState = GraphUiState.Success(
            SuccessData(
                newCases = generateRandomDataPoints(),
                cumulativeCases = generateRandomDataPoints(),
                newDeaths = generateRandomDataPoints(),
                cumulativeDeaths = generateRandomDataPoints()
            )
        )
    }

    fun updateCountry(country: String) {
        selectedCountry.value = country
        graphUiState = GraphUiState.Loading
        lastUpdated.value = "loading..."
        totalCases.value = 0
        totalDeaths.value = 0
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(1000)
            graphUiState = GraphUiState.Success(
                SuccessData(
                    newCases = generateRandomDataPoints(),
                    cumulativeCases = generateRandomDataPoints(),
                    newDeaths = generateRandomDataPoints(),
                    cumulativeDeaths = generateRandomDataPoints()
                )
            )
            lastUpdated.value = "2000-01-01"
            totalCases.value = (100_000..1_000_000).random()
            totalDeaths.value = (100_000..1_000_000).random()
        }
    }
}
