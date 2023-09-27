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
import androidx.compose.ui.text.style.TextOverflow
import com.example.covid.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSection(
    appViewModel: AppViewModel,
    onNavIconClicked: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    LaunchedEffect(appViewModel.scrollState.value) {
        val threshold = 350
        if (appViewModel.scrollState.value > threshold) {
            title = appViewModel.selectedCountry.value
        } else {
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