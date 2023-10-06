package com.example.covid.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.covid.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSection(
    appViewModel: AppViewModel,
    onNavIconClicked: () -> Unit,
    onInfoIconClicked: () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    LaunchedEffect(appViewModel.scrollState.value) {
        val threshold = 350
        if (appViewModel.scrollState.value > threshold) {
            title = appViewModel.selectedCountry.value
            visible = true
        } else {
            visible = false
        }
    }

    CenterAlignedTopAppBar(title = {
        AnimatedVisibility(
            visible = visible, enter = fadeIn(), exit = fadeOut()
        ) {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }, navigationIcon = {
        IconButton(onClick = { onNavIconClicked() }) {
            Icon(
                imageVector = Icons.Filled.Menu, contentDescription = "Localized description"
            )
        }
    }, actions = {
        IconButton(onClick = { onInfoIconClicked() }) {
            Icon(
                imageVector = Icons.Outlined.Info, contentDescription = "Localized description"
            )
        }
    })
}