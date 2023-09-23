package com.example.covid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.covid.ui.components.GraphCard
import com.example.covid.ui.components.LocationCard
import com.example.covid.ui.functions.generateRandomDataPoints
import com.example.covid.ui.sections.CaseDeathCards
import com.example.covid.ui.sections.TopBar
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
                    CovidAppV2()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CovidAppV2() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedCountry by remember { mutableStateOf("USA") }
    val items = listOf(
        "USA", "India", "Brazil", "France"
    )
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(Modifier.height(12.dp))
            items.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(item) },
                    selected = item == selectedItem.value,
                    onClick = {
                        scope.launch { drawerState.close() }
                        selectedItem.value = item
                        selectedCountry = item
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }, content = {
        Column {
            TopBar(onNavIconClicked = {
                scope.launch { drawerState.open() }
            })
            BelowTopBar(
                selectedCountry = selectedCountry
            )
        }
    })
}

@Composable
fun BelowTopBar(
    modifier: Modifier = Modifier, selectedCountry: String = "USA"
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        item {
            LocationCard(
                modifier = Modifier.padding(
                    top = 64.dp,
                    bottom = 64.dp
                ), selectedCountry
            )
            CaseDeathCards(
                cases = (0..1000000).random(),
                deaths = (0..1000000).random(),
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
        item {
            GraphCard(generateRandomDataPoints(), description = "Cases").New()
            Spacer(modifier = Modifier.padding(8.dp))
        }
        item {
            GraphCard(generateRandomDataPoints(), description = "Cured").New()
            Spacer(modifier = Modifier.padding(8.dp))
        }
        item {
            GraphCard(generateRandomDataPoints(), description = "Treating").New()
            Spacer(modifier = Modifier.padding(8.dp))
        }
        item {
            GraphCard(generateRandomDataPoints(), description = "Deaths").New()
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
