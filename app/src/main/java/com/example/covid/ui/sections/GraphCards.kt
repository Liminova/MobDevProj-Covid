package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.covid.ui.components.GraphCard
import com.patrykandpatrick.vico.core.entry.FloatEntry

private fun generateRandomDataPoints(): List<FloatEntry> {
    val dataPoints = arrayListOf<FloatEntry>()
    var xPos = 0f
    for (i in 1..100) {
        val randomYFloat = (1..1000).random().toFloat()
        dataPoints.add(FloatEntry(x = xPos, y = randomYFloat))
        xPos += 1f
    }
    return dataPoints
}

@Composable
fun GraphCards(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        items(4) {
            GraphCard(generateRandomDataPoints()).New()
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
