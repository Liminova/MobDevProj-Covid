package com.example.covid.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.covid.rememberMarker
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun GraphCard(
    modifier: Modifier = Modifier,
    dataPoints: List<FloatEntry>,
    onButtonClick: () -> Unit = {},
    description: String,
) {
    val refreshDataSet = remember { mutableIntStateOf(0) }
    val modelProducer = remember { ChartEntryModelProducer() }
    val datasetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
    val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }
    val scrollState = rememberChartScrollState()
    LaunchedEffect(key1 = refreshDataSet.intValue) {
        datasetForModel.clear()
        datasetLineSpec.clear()
        datasetLineSpec.add(
            LineChart.LineSpec(
                lineColor = Color.Green.toArgb(), lineBackgroundShader = DynamicShaders.fromBrush(
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
                    val marker = rememberMarker()
                    Chart(
                        chart = lineChart(
                            lines = datasetLineSpec
                        ),
                        chartModelProducer = modelProducer,

                        // AXIS:
                        startAxis = rememberStartAxis(
                            title = "Cases",
                            tickLength = 0.dp,
                            valueFormatter = { value, _ ->
                                ((value.toInt()) + 1).toString()
                            },
                            itemPlacer = AxisItemPlacer.Vertical.default(
                                maxItemCount = 6,
                            )
                        ),

                        bottomAxis = rememberBottomAxis(
                            title = "Date",
                            tickLength = 0.dp,
                            valueFormatter = { value, _ ->
                                value.toString()
                            },
                        ),

                        // MARKER:
                        marker = marker,

                        chartScrollState = scrollState,
                        isZoomEnabled = true,
                    )
                }
            }
            TextButton(modifier = Modifier.fillMaxWidth(), onClick = { onButtonClick }) {
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}