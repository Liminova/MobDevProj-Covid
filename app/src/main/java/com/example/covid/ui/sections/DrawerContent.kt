package com.example.covid.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.covid.ui.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    appViewModel: AppViewModel, scope: CoroutineScope, drawerState: DrawerState
) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredCountries =
        appViewModel.countriesListMap.filter { it.value.contains(searchQuery, true) }
    ModalDrawerSheet {
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
            if (appViewModel.readyToComposeCountryList.intValue == 0) {
                NavigationDrawerItem(label = { Text("loading...") },
                    selected = false,
                    onClick = { })
                return@ModalDrawerSheet
            }
            val temp = filteredCountries.ifEmpty { appViewModel.countriesListMap }
            temp.forEach { (countryCode, countryName) ->
                NavigationDrawerItem(
                    label = { Text(countryName) },
                    selected = countryName == appViewModel.selectedCountry.value,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            appViewModel.updateCountry(countryCode, countryName)
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}