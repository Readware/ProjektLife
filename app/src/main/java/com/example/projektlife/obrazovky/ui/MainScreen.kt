package com.example.projektlife.obrazovky.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.github.tehras.charts.piechart.PieChart
import com.example.projektlife.R



@Composable
fun MainScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.MainScreen) }

    Scaffold(
        floatingActionButton = { ExpandableFloatingActionButton(navController) },
        bottomBar = {
            BottomNavBar(selectedItem = selectedItem) { item ->
                selectedItem = item
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TopSection()
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}



@Composable
fun ExpandableFloatingActionButton(navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp)
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



enum class BottomNavItem(val label: String, val icon: ImageVector) {
    Statistics("Štatistiky", Icons.Default.DateRange),
    MainScreen("Domov", Icons.Default.Home),
    CategoryView("Kategórie", Icons.Default.List),
    Settings("Nastavenia", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = item == selectedItem,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

@Composable
fun TopSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7))
            .padding(30.dp)
    ) {
        PieChart(
            pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice(value = 30f, color = Color.Red),
                    PieChartData.Slice(value = 20f, color = Color.Green),
                    PieChartData.Slice(value = 50f, color = Color.Blue)
                )
            ),
            modifier = Modifier.size(200.dp),
            sliceDrawer = SimpleSliceDrawer()
        )

        Image(
            painter = painterResource(id = R.drawable.pfp),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}


