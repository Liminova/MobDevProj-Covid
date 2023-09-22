package com.example.covid.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.ChartScrollState
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun GraphCard(
    datasetForModel: SnapshotStateList<List<FloatEntry>>,
    datasetLineSpec: List<LineChart.LineSpec>,
    modelProducer: ChartEntryModelProducer,
    scrollState: ChartScrollState,
    refreshDataSet: MutableIntState,
    modifier: Modifier = Modifier,
    description: String = "This is the default description",
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(
                start = 16.dp,
                end = 16.dp,
            )
    ) {
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
    }
    TextButton(modifier = Modifier.fillMaxWidth(), onClick = { refreshDataSet.intValue++ }) {
        Text(text = description, textAlign = TextAlign.Center)
    }
}