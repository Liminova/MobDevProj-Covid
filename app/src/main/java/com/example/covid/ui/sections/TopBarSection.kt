package com.example.covid.ui.sections

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.style.TextOverflow
import com.example.covid.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSection(
    appViewModel: AppViewModel,
    onNavIconClicked: () -> Unit,
) {
    LaunchedEffect(appViewModel.scrollState.value) {
        val yourThreshold = 350
        if (appViewModel.scrollState.value > yourThreshold) {
            appViewModel.topBarTitle.value = appViewModel.selectedCountry.value
        } else {
            appViewModel.topBarTitle.value = ""
        }
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = appViewModel.topBarTitle.value,
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