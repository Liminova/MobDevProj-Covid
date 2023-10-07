package com.example.covid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.covid.R
import com.example.covid.ui.navigation.CovidAppRouter
import com.example.covid.ui.navigation.Screen
import com.example.covid.ui.navigation.SystemBackButtonHandler

@Composable
fun InfoScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.about_us),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 96.dp, bottom = 96.dp)
            )
            Text(
                text = stringResource(R.string.about_us_p1),
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.about_us_p2),
                textAlign = TextAlign.Justify
            )
        }
    }
    SystemBackButtonHandler {
        CovidAppRouter.navigateTo(Screen.StatScreen)
    }
}