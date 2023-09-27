package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.covid.R
import com.example.covid.ui.components.CountCard

@Composable
fun CountSection(
    modifier: Modifier = Modifier,
    cumulatedDeaths: Int = 0,
    cumulatedCases: Int = 0,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        CountCard(
            label = stringResource(R.string.total_cases),
            count = cumulatedCases,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
        CountCard(
            label = stringResource(R.string.total_deaths),
            count = cumulatedDeaths,
            modifier = Modifier.weight(1f)
        )
    }
}