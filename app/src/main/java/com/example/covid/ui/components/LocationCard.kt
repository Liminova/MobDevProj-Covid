package com.example.covid.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LocationCard(modifier: Modifier = Modifier, selectedCountry: String = "USA") {
    Text(
        modifier = modifier,
        text = selectedCountry,
        style = MaterialTheme.typography.displayLarge,
        fontWeight = FontWeight.Bold
    )
}