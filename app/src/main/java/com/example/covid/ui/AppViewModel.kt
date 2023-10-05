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

    init {
        viewModelScope.launch {
            countriesListMap = try {
                CovidApi.retrofitService.getCountries() as MutableMap<String, String>
            } catch (e: Exception) {
                mapOf("Cannot fetch API" to "") as MutableMap<String, String>
            }
        }
    }

    fun updateCountry(country: Map<String, String>) {
        if (country.values.first() == "") return
        selectedCountry.value = country.keys.first()
        lastUpdated.value = "loading..."
        lastUpdated.value = "2000-01-01"
        totalCases.intValue = 0
        totalDeaths.intValue = 0
        graphUiState = GraphUiState.Loading

        viewModelScope.launch {
            val countryData = CovidApi.retrofitService.getCountryData(country.values.toList()[0])
            lastUpdated.value = countryData.reports.last().date
            totalCases.intValue = countryData.reports.last().cumulativeCases
            totalDeaths.intValue = countryData.reports.last().cumulativeDeaths

            fun dateAxisMapper(countryReports: List<Report>): Map<Date, Float> {
                return countryReports.associate {
                    val date = it.date.split("-")
                    Date(
                        date[0].toInt(), date[1].toInt(), date[2].toInt()
                    ) to it.newCases.toFloat()
                }
            }

            graphUiState = GraphUiState.Success(
                SuccessData(
                    newCases = dateAxisMapper(countryData.reports),
                    cumulativeCases = dateAxisMapper(countryData.reports),
                    newDeaths = dateAxisMapper(countryData.reports),
                    cumulativeDeaths = dateAxisMapper(countryData.reports),
                )
            )
        }
    }
}
