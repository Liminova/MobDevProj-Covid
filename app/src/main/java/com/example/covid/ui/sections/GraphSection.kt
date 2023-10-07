package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.covid.R
import com.example.covid.dataclasses.Date
import com.example.covid.ui.AppViewModel
import com.example.covid.ui.components.GraphCard

@Composable
fun GraphSection(
    modifier: Modifier = Modifier, viewModel: AppViewModel,
) {
    when (viewModel.graphUiState) {
        is GraphUiState.Loading -> Loading(modifier)
        is GraphUiState.Success -> Success(
            (viewModel.graphUiState as GraphUiState.Success).data, modifier
        )

        is GraphUiState.Error -> Error(
            (viewModel.graphUiState as GraphUiState.Error).message, modifier
        )
    }
}

sealed interface GraphUiState {
    object Loading : GraphUiState
    data class Success(val data: SuccessData) : GraphUiState
    data class Error(val message: String) : GraphUiState
}

data class SuccessData(
    val newCasesLast6M: Map<Date, Int>,
    val newDeathsLast6M: Map<Date, Int>,
    val newCasesAllTime: Map<Date, Int>,
    val newDeathsAllTime: Map<Date, Int>,
)

@Composable
private fun Success(
    data: SuccessData, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.newCasesLast6M,
            description = stringResource(
                R.string.new_cases_last_6_months
            )
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.newDeathsLast6M,
            description = stringResource(R.string.new_deaths_last_6_months)
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.newCasesAllTime,
            description = stringResource(R.string.new_cases_all_time)
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.newDeathsAllTime,
            description = stringResource(R.string.new_deaths_all_time)
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 64.dp))
    }
}

@Composable
private fun Error(message: String, modifier: Modifier = Modifier) {
    Text(text = message, modifier = modifier)
}