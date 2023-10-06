package com.example.covid.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covid.ui.AppViewModel
import com.example.covid.ui.components.LocationCard
import com.example.covid.ui.sections.CountSection
import com.example.covid.ui.sections.DrawerContent
import com.example.covid.ui.sections.GraphSection
import com.example.covid.ui.sections.TopBarSection
import kotlinx.coroutines.launch

@Composable
fun CovidApp() {
    val appViewModel = viewModel<AppViewModel>()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = { DrawerContent(appViewModel, scope, drawerState) }) {
        Column {
            TopBarSection(
                appViewModel,
                onNavIconClicked = { scope.launch { drawerState.open() } },
                onInfoIconClicked = {}
            )
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(appViewModel.scrollState)
            ) {
                LocationCard(
                    Modifier.padding(top = 64.dp, bottom = 96.dp),
                    appViewModel.selectedCountry.value,
                    appViewModel.lastUpdated.value,
                )
                CountSection(
                    Modifier.padding(bottom = 16.dp),
                    appViewModel.totalDeaths.intValue,
                    appViewModel.totalCases.intValue,
                    appViewModel.totalCasesLast30d.intValue,
                    appViewModel.fatalityRate.floatValue,
                )
                GraphSection(Modifier.fillMaxWidth(), appViewModel)
            }
        }
    }
}