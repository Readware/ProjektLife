package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        floatingActionButton = { ExpandableFloatingActionButton(navController) },
    ){ paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Text(text = "Vitajte v aplikácii ")
        }
    }
}

@Composable
fun ExpandableFloatingActionButton(navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 50.dp) //Nastavuje kde sa ma tlacidlo plus nachadzat
    ) {
        if (isExpanded) {
            FloatingActionButton(onClick = { navController.navigate("add_kategoria") }) {
                Text(" Vytvor kategóriu ")
            }

            FloatingActionButton(onClick = { navController.navigate("add_aktivita") }) {
                Text(" Vytvor aktivitu ")
            }
        }

        FloatingActionButton(onClick = { isExpanded = !isExpanded }) {
            Icon(Icons.Filled.Add, contentDescription = "Options")
        }
    }
}
