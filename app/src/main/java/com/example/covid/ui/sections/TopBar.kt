package com.example.covid.ui.sections

import androidx.compose.foundation.ScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onNavIconClicked: () -> Unit,
    scrollState: ScrollState,
    selectedCountry: String
) {
    //Update title based on scroll state
    val title = remember { mutableStateOf("COVID-19 Statistics") }
    LaunchedEffect(scrollState.value) {
        val yourThreshold = 350
        if (scrollState.value > yourThreshold) {
            title.value = selectedCountry
        } else {
            title.value = "COVID-19 Statistics"
        }
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title.value,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavIconClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}