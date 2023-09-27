package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.covid.ui.functions.generateRandomDataPoints
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

class GraphSectionViewModel : ViewModel() {
    var graphUiState: GraphUiState = GraphUiState.Loading
        private set

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
}

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