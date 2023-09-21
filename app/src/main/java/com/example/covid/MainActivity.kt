package com.example.covid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.covid.ui.theme.CovidTheme
import com.lighttigerxiv.catppuccin_kt.Label
import com.lighttigerxiv.catppuccin_kt.getHexColor
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CovidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CovidApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.top_bar),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(getHexColor(Label.MACCHIATO_SURFACE0).asLong()),
            titleContentColor = Color(getHexColor(Label.MACCHIATO_TEXT).asLong()),
            navigationIconContentColor = Color(getHexColor(Label.MACCHIATO_TEXT).asLong())
        )
    )
}

@Composable
fun CountBox(
    modifier: Modifier = Modifier,
    label: String,
    count: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = label,
        )
        Text(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = count.toString(),
        )
    }
}


@Composable
fun DisplayCount(
    modifier: Modifier = Modifier,
    cases: Int,
    deaths: Int,
) {
    Row(
        modifier = modifier,
    ) {
        CountBox(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            label = "Cases",
            count = cases,
        )
        CountBox(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            label = "Deaths",
            count = deaths,
        )
    }
}

@Composable
fun DisplayGraph(
    modifier: Modifier = Modifier,
) {
    val refreshDataSet = remember { mutableIntStateOf(0) }
    val modelProducer = remember { ChartEntryModelProducer() }
    val datasetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
    val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

    val scrollState = rememberChartScrollState()
    LaunchedEffect(key1 = refreshDataSet.intValue) {
        // TODO: rebuild dataset
        datasetForModel.clear()
        datasetLineSpec.clear()
        var xPos = 0f
        val dataPoints = arrayListOf<FloatEntry>()
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
        for (i in 1..100) {
            val randomYFloat = (1..1000).random().toFloat()
            dataPoints.add(FloatEntry(x = xPos, y = randomYFloat))
            xPos += 1f
        }

        datasetForModel.add(dataPoints)

        modelProducer.setEntries(datasetForModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
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
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { refreshDataSet.intValue++ }
        ) {
            Text(text = "Another One - DJ Khaled", textAlign = TextAlign.Center)
        }
        // Another One
        Card(
            modifier = Modifier
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
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { refreshDataSet.intValue++ }
        ) {
            Text(text = "Another One - DJ Khaled", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CovidApp(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        item {
            TopBar()
        }

        item {
            DisplayCount(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                cases = 100,
                deaths = 200,
            )
        }

        item {
            DisplayGraph(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CovidAppPreview() {
    CovidTheme {
        CovidApp()
    }
}
