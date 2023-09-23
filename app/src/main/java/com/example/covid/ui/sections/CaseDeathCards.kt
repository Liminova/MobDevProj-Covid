package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.covid.R
import com.example.covid.ui.components.CountCard

@Composable
fun CaseDeathCards(
    modifier: Modifier = Modifier,
    cases: Int,
    deaths: Int,
) {
    Row(
        modifier = modifier,
    ) {
        CountCard(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            label = stringResource(R.string.cases),
            count = cases,
        )
        CountCard(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            label = stringResource(R.string.deaths),
            count = deaths,
        )
    }
}