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
import androidx.compose.material3.DrawerState
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
import com.example.covid.ui.sections.CountSection
import com.example.covid.ui.sections.GraphSection
import com.example.covid.ui.sections.TopBarSection
import com.example.covid.ui.theme.CovidTheme
import kotlinx.coroutines.CoroutineScope
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
//    val context = LocalContext.current
    val appViewModel = viewModel<AppViewModel>()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = { DrawerContent(appViewModel, scope, drawerState) }) {
        Column {
            TopBarSection(
                appViewModel,
                onNavIconClicked = { scope.launch { drawerState.open() } },
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
                )
                GraphSection(Modifier.fillMaxWidth(), appViewModel)
            }
        }
    }
}

@Composable
fun DrawerContent(
    appViewModel: AppViewModel, scope: CoroutineScope, drawerState: DrawerState
) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredCountries = if (searchQuery.isEmpty()) {
        appViewModel.countriesListMap
    } else {
        appViewModel.countriesListMap.filter { it.value.contains(searchQuery, true) }
    }
    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        TextField(value = searchQuery,
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
            if (appViewModel.readyToComposeCountryList.intValue == 0) {
                NavigationDrawerItem(label = { Text("loading...") },
                    selected = false,
                    onClick = { })
                return@ModalDrawerSheet
            }
            filteredCountries.forEach { (countryCode, countryName) ->
                NavigationDrawerItem(
                    label = { Text(item.value) },
                    selected = item.value == appViewModel.selectedCountry.value,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            appViewModel.updateCountry(mapOf(item.key to item.value))
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}
