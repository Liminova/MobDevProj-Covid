package com.example.covid.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.covid.dataclasses.Date
import com.example.covid.rememberMarker
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

class CovidEntry(
    private val date: Date,
    private val cases: Int,
    override val x: Float = date.intToEpochDate().toFloat(),
    override val y: Float = cases.toFloat(),
) : ChartEntry {
    override fun withY(y: Float): ChartEntry {
        return CovidEntry(date, cases, x, y)
    }
}

@Composable
fun GraphCard(
    modifier: Modifier = Modifier,
    dataPoints: Map<Date, Int>,
    onButtonClick: () -> Unit = {},
    description: String,
) {
    var useBarChart by remember { mutableStateOf(false) }
    val scrollState = rememberChartScrollState()
    val datasetLineStyle = arrayListOf(
        LineChart.LineSpec(
            lineColor = Color.Green.toArgb(),
            lineBackgroundShader = DynamicShaders.fromBrush(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                        Color.Green.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                    )
                )
            ),
        )
    )
    val chart = if (useBarChart) {
        columnChart(spacing = 4.dp)
    } else {
        lineChart(lines = datasetLineStyle)
    }

    val modelProducer =
        ChartEntryModelProducer(dataPoints.map { (key, value) -> CovidEntry(key, value) })
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Card {
            if (dataPoints.isEmpty()) {
                Text(
                    text = "No data available",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                return@Card
            }
            ProvideChartStyle {
                val marker = rememberMarker()
                Chart(
                    chart = chart,
                    chartModelProducer = modelProducer,

                    // AXIS:
                    startAxis = rememberStartAxis(
                        title = "Cases", tickLength = 0.dp, valueFormatter = { value, _ ->
                            ((value.toInt()) + 1).toString()
                        }, itemPlacer = AxisItemPlacer.Vertical.default(
                            maxItemCount = 6,
                        )
                    ),

                    bottomAxis = rememberBottomAxis(
                        title = "Date",
                        tickLength = 0.dp,
                        valueFormatter = { value, _ ->
                            Date().epochDateToString(value.toLong())
                        },
                    ),

                    // MARKER:
                    marker = marker,
                    chartScrollState = scrollState,
                    isZoomEnabled = true,
                )
            }

            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                useBarChart = !useBarChart
                onButtonClick()
            }) {
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 16.dp)
                )
            }
        }
    }
}