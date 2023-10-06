package com.example.covid.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid.dataclasses.Date
import com.example.covid.dataclasses.Report
import com.example.covid.network.CovidApi
import com.example.covid.ui.sections.GraphUiState
import com.example.covid.ui.sections.SuccessData
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
        private set
    var countriesListMap = mutableMapOf<String, String>()
        private set
    var readyToComposeCountryList = mutableIntStateOf(0)
        private set

    init {
        viewModelScope.launch {
            countriesListMap = try {
                CovidApi.retrofitService.getCountries() as MutableMap<String, String>
            } catch (e: Exception) {
                mapOf("" to "Cannot fetch API") as MutableMap<String, String>
            }
            readyToComposeCountryList.intValue = 1
        }
        updateCountry("VN", "Vietnam")
    }

    fun updateCountry(countryCode: String, countryName: String) {
        if (countryCode == "" || countryName == "") return
        selectedCountry.value = countryName
        lastUpdated.value = "loading..."
        lastUpdated.value = "loading..."
        totalCases.intValue = 0
        totalDeaths.intValue = 0
        graphUiState = GraphUiState.Loading

        viewModelScope.launch {
            val countryData = CovidApi.retrofitService.getCountryData(countryCode)
            val dayRetention = 60
            val reports = if (countryData.reports.size > dayRetention) {
                countryData.reports.subList(
                    countryData.reports.size - dayRetention, countryData.reports.size
                )
            } else {
                countryData.reports
            }

            lastUpdated.value = reports.last().date
            totalCases.intValue = reports.last().cumulativeCases
            totalDeaths.intValue = reports.last().cumulativeDeaths

            graphUiState = GraphUiState.Success(
                SuccessData(
                    dateAxisMapper(reports, "newCases"),
                    dateAxisMapper(reports, "newDeaths"),
                )
            )
        }
    }

    private fun dateAxisMapper(countryReports: List<Report>, typeOfData: String): Map<Date, Int> {
        return countryReports.associate {
            val dateRaw = it.date.split("-").map { it2 -> it2.toInt() }
            val date = Date(dateRaw[0], dateRaw[1], dateRaw[2])
            date to when (typeOfData) {
                "newCases" -> it.newCases
                "newDeaths" -> it.newDeaths
                else -> 0
            }
        }
    }
}
