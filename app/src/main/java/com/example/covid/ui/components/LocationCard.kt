package com.example.covid.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.covid.R

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    countryName: String,
    lastUpdated: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = countryName,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.last_updated, lastUpdated),
        )
    }
}