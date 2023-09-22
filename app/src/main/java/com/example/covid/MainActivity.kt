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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
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
import com.example.covid.ui.sections.GraphCards
import com.example.covid.ui.sections.TopBar
import com.example.covid.ui.sections.TopCards
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

@Composable
fun CardLocation(modifier: Modifier = Modifier, selectedCountry: String = "USA") {
    Text(
        text = selectedCountry,
        modifier = modifier
            .padding(16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}


@Preview(showBackground = true)
@Composable
fun CovidAppV2() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedCountry by remember { mutableStateOf("USA") }
    val items = listOf(
        "USA",
        "India",
        "Brazil",
        "France"
    )
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
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
        },
        content = {
            Column {
                TopBar(
                    onNavIconClicked = {
                        scope.launch { drawerState.open() }
                    }
                )
                CardLocation(
                    modifier = Modifier
                        .padding(16.dp),
                    selectedCountry = selectedCountry
                )
                TopCards(
                    modifier = Modifier
                        .padding(16.dp),
                    // randomize numbers of cases and deaths
                    cases = (0..1000000).random(),
                    deaths = (0..1000000).random(),
                )
                GraphCards(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    )
}

