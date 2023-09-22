package com.example.covid.ui.sections

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.covid.R
import com.lighttigerxiv.catppuccin_kt.Label
import com.lighttigerxiv.catppuccin_kt.getHexColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onNavIconClicked: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.top_bar),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = {
            onNavIconClicked()
        }) {
            Icon(
                imageVector = Icons.Filled.Menu, contentDescription = "Localized description"
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(getHexColor(Label.MACCHIATO_SURFACE0).asLong()),
        titleContentColor = Color(getHexColor(Label.MACCHIATO_TEXT).asLong()),
        navigationIconContentColor = Color(getHexColor(Label.MACCHIATO_TEXT).asLong())
    )
    )
}