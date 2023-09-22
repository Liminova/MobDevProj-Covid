package com.example.covid.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
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

class GraphCard(
    private val dataPoints: List<FloatEntry>,
    private val onButtonClick: () -> Unit = {},
    private val description: String = "This is the default description",
    private val modifier: Modifier = Modifier,
) {
    @Composable
    fun New() {
        val refreshDataSet = remember { mutableIntStateOf(0) }
        val modelProducer = remember { ChartEntryModelProducer() }
        val datasetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
        val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }
        val scrollState = rememberChartScrollState()
        LaunchedEffect(key1 = refreshDataSet.intValue) {
            // TODO: rebuild dataset
            datasetForModel.clear()
            datasetLineSpec.clear()
            datasetLineSpec.add(
                LineChart.LineSpec(
                    lineColor = Color.Green.toArgb(),
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                            )
                        )
                    )
                )
            )
            datasetForModel.add(dataPoints)
            modelProducer.setEntries(datasetForModel)
        }
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Card {
                if (datasetForModel.isNotEmpty()) {
                    ProvideChartStyle {
                        Chart(
                            chart = lineChart(
                                lines = datasetLineSpec
                            ),
                            chartModelProducer = modelProducer,
                            chartScrollState = scrollState,
                            isZoomEnabled = true,
                        )
                    }
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onButtonClick }) {
                    Text(text = description, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
