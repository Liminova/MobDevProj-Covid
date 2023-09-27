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
import androidx.compose.ui.unit.dp
import com.example.covid.ui.AppViewModel
import com.example.covid.ui.components.GraphCard
import com.patrykandpatrick.vico.core.entry.FloatEntry


data class SuccessData(
    val newCases: List<FloatEntry>,
    val cumulativeCases: List<FloatEntry>,
    val newDeaths: List<FloatEntry>,
    val cumulativeDeaths: List<FloatEntry>,
)

sealed interface GraphUiState {
    object Loading : GraphUiState
    data class Success(val data: SuccessData) : GraphUiState
    data class Error(val message: String) : GraphUiState
}

@Composable
fun GraphSection(
    viewModel: GraphSectionViewModel, modifier: Modifier = Modifier
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

}

@Composable
private fun Success(
    data: SuccessData, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GraphCard(
            Modifier.padding(bottom = 16.dp), dataPoints = data.newCases, description = "New Cases"
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.cumulativeCases,
            description = "Cumulative Cases"
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.newDeaths,
            description = "New Deaths"
        )
        GraphCard(
            Modifier.padding(bottom = 16.dp),
            dataPoints = data.cumulativeDeaths,
            description = "Cumulative Deaths"
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Text(text = "Loading...", modifier = modifier)
}

@Composable
private fun Error(message: String, modifier: Modifier = Modifier) {
    Text(text = message, modifier = modifier)
}