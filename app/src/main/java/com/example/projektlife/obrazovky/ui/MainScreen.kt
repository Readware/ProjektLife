package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import coil.compose.rememberImagePainter
import com.example.projektlife.R

val pieChartData = PieChartData(
    slices = listOf(
        PieChartData.Slice(value = 30f, color = Color.Red),
        PieChartData.Slice(value = 20f, color = Color.Green),
        PieChartData.Slice(value = 50f, color = Color.Blue)
    )
)

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        floatingActionButton = { ExpandableFloatingActionButton(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            TopSection()
        }
    }
}
@Composable
fun ExpandableFloatingActionButton(navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 50.dp)
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


