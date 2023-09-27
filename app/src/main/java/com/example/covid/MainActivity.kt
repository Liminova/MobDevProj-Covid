package com.example.covid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covid.ui.AppViewModel
import com.example.covid.ui.components.LocationCard
import com.example.covid.ui.countries
import com.example.covid.ui.sections.CountSection
import com.example.covid.ui.sections.GraphSection
import com.example.covid.ui.sections.TopBarSection
import com.example.covid.ui.theme.CovidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CovidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CovidApp()
                }
            }
        }
    }
}

@Composable
fun CovidApp() {
    val appViewModel = viewModel<AppViewModel>()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val countries = countries

    var searchQuery by remember { mutableStateOf("") }
    val filteredCountries = countries.filter { it.contains(searchQuery, ignoreCase = true) }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(content = {
            Spacer(Modifier.height(12.dp))
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                filteredCountries.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item) },
                        selected = item == appViewModel.selectedCountry.value,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                appViewModel.updateCountry(item)
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        })
    }, content = {
        Column {
            TopBarSection(
                appViewModel,
                onNavIconClicked = { scope.launch { drawerState.open() } },
            )
            BelowTopBar(Modifier, appViewModel)
        }
    })
}

@Composable
fun BelowTopBar(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
) {
    Column(
        modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(appViewModel.scrollState)
    ) {
        LocationCard(
            modifier = Modifier.padding(
                top = 64.dp, bottom = 96.dp
            ),
            appViewModel.selectedCountry.value,
            appViewModel.lastUpdated.value,
        )
        CountSection(
            appViewModel.totalDeaths.value,
            appViewModel.totalCases.value,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        GraphSection(appViewModel, modifier.fillMaxWidth())
    }
}
