package com.example.covid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.covid.ui.functions.ReadableNumber

@Composable
fun CountCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Float,
    roundValueToInt: Boolean = true,
    postFix: String = "",
) {
    val formattedValue = if (roundValueToInt) ReadableNumber(value.toInt()) else value
    Card(modifier) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
                start = 16.dp,
            ),
            style = MaterialTheme.typography.titleMedium
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                formattedValue.toString() + postFix,
                modifier = Modifier.padding(
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                ),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}