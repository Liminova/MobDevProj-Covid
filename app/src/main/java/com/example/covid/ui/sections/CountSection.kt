package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
    casesLast30d: Int = 0,
    fatalityRate: Float = 0f,
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CountCard(
                label = stringResource(R.string.total_cases),
                value = cumulatedCases.toFloat(),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            CountCard(
                label = stringResource(R.string.total_deaths),
                value = cumulatedDeaths.toFloat(),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CountCard(
                label = stringResource(R.string.fatality_rate),
                value = fatalityRate,
                postFix = "%",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                roundValueToInt = false
            )
            CountCard(
                label = stringResource(R.string.cases_last_30d),
                value = casesLast30d.toFloat(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}