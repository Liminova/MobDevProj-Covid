package com.example.covid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                text = stringResource(R.string.disclaimer),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.disclaimer_text),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
            Text(
                text = stringResource(R.string.data_source),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.data_source_text),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
        }
    }
    SystemBackButtonHandler {
        CovidAppRouter.navigateTo(Screen.StatScreen)
    }
}