package com.example.projektlife.obrazovky.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.R
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.viewmodel.AktivitaView
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer


@Composable
fun MainScreen(navController: NavHostController, aktivitaView: AktivitaView = viewModel()) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.MainScreen) }
    val uiState by aktivitaView.uiState.collectAsState()

    LaunchedEffect(Unit) {
        aktivitaView.getAllAktivitas()
    }

    Scaffold(
        floatingActionButton = { ExpandableFloatingActionButton(navController) },
        bottomBar = {
            BottomNavBar(selectedItem = selectedItem) { item ->
                selectedItem = item
                when (item) {
                    BottomNavItem.Statistics -> navController.navigate("statistics")
                    BottomNavItem.MainScreen -> navController.navigate("main_screen")
                    BottomNavItem.CategoryView -> navController.navigate("kategorie_uprava")
                    BottomNavItem.Settings -> navController.navigate("settings")
                }
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
                AktivitaList(aktivitaView = aktivitaView)
            }
        }
    }
}
@Composable
fun AktivitaList(aktivitaView: AktivitaView) {
    val uiState by aktivitaView.uiState.collectAsState()

    LazyColumn {
        items(uiState.aktivity) { aktivita ->
            val kategoria = uiState.kategorie[aktivita.kategoriaId]
            AktivitaItem(
                aktivita = aktivita,
                kategoria = kategoria,
                onDelete = { aktivitaView.deleteAktivita(aktivita) },
                onClick = {  }
            )
        }
    }
}


@Composable
fun AktivitaItem(aktivita: Aktivita, kategoria: Kategoria?, onDelete: () -> Unit, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        val typeColor = when (kategoria?.typ) {
            "POSITIVNA" -> Color.Green
            "NEGATIVNA" -> Color.Red
            "NEUTRALNA" -> Color.Gray
            else -> Color.Gray
        }

        val typeIcon = when (kategoria?.typ) {
            "POSITIVNA" -> Icons.Default.Check
            "NEGATIVNA" -> Icons.Default.Clear
            "NEUTRALNA" -> Icons.Default.KeyboardArrowRight
            else -> Icons.Default.Info
        }

        Icon(
            imageVector = typeIcon,
            contentDescription = null,
            tint = typeColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = aktivita.nazov)

        Spacer(modifier = Modifier.weight(1f))

        val buttonColor = kategoria?.farba?.let { parseColor(it) } ?: Color.Gray
        val buttonIcon = if (aktivita.jednorazova) Icons.Default.Add else Icons.Default.AddCircle

        IconButton(onClick = { onDelete() }) {
            Icon(
                imageVector = buttonIcon,
                contentDescription = null,
                tint = buttonColor,
                modifier = Modifier.size(24.dp)
            )
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


