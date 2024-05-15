package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.UlozeneView
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    navController: NavHostController,
    aktivitaView: AktivitaView = viewModel(),
    ulozeneViewModel: UlozeneView = viewModel()
) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.MainScreen) }


    // Načítanie všetkých aktivít pri spustení composable
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
                    BottomNavItem.Settings -> navController.navigate("nastavenia")
                }
            }
        }
    ) { innerPadding ->
        // Rozloženie pre landscape režim
        if (isLandscape()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AktivitaList(
                            aktivitaView = aktivitaView,
                            ulozeneViewModel = ulozeneViewModel
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    TopSection(ulozeneViewModel = ulozeneViewModel)
                }
            }
        } else {
            // Rozloženie pre portrait režim
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TopSection(ulozeneViewModel = ulozeneViewModel)
                Box(modifier = Modifier.fillMaxSize()) {
                    AktivitaList(aktivitaView = aktivitaView, ulozeneViewModel = ulozeneViewModel)
                }
            }
        }
    }
}

@Composable
fun AktivitaList(aktivitaView: AktivitaView, ulozeneViewModel: UlozeneView) {
    val uiState by aktivitaView.uiState.collectAsState()
    var padd = 0.dp
    if (isLandscape()) {
        padd = 30.dp
    }
    // Zobrazenie zoznamu aktivít
    LazyColumn(modifier = Modifier.padding(top = padd)) {
        items(uiState.aktivity) { aktivita ->
            val kategoria = uiState.kategorie[aktivita.kategoriaId]
            AktivitaItem(
                aktivita = aktivita,
                kategoria = kategoria,
                onDelete = { aktivitaView.deleteAktivita(aktivita) },
                onClick = { },
                ulozeneViewModel = ulozeneViewModel,
                aktivitaViewModel = aktivitaView
            )
        }
    }
}

@Composable
fun AktivitaItem(
    aktivita: Aktivita,
    kategoria: Kategoria?,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    ulozeneViewModel: UlozeneView,
    aktivitaViewModel: AktivitaView = viewModel()
) {
    var offsetX by remember { mutableStateOf(0f) }
    val swipeThreshold = -100f
    val maxOffsetX = -200f

    // Box komponent pre swipe gestá a zobrazenie detailov aktivity
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceIn(maxOffsetX, 0f)
                    },
                    onDragEnd = {
                        if (offsetX < swipeThreshold) {
                            offsetX = maxOffsetX
                        } else {
                            offsetX = 0f
                        }
                    }
                )
            }
    ) {
        Row(//Nastavovanie výzoru tlačidla podľa jeho vlastností
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxWidth()
                .clickable { onClick() }
                .background(Color.White)
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

            Column {
                Text(
                    text = aktivita.nazov,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = "Váha: ${aktivita.vaha}", style = MaterialTheme.typography.body2)
                Text(
                    text = "Kategória: ${kategoria?.nazov}",
                    style = MaterialTheme.typography.body2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonColor = kategoria?.farba?.let { parseColor(it) } ?: Color.Gray
            val buttonIcon =
                if (aktivita.jednorazova) Icons.Default.Add else Icons.Default.AddCircle

            IconButton(onClick = {
                val today = Date()

                ulozeneViewModel.addUlozene(
                    aktivitaId = aktivita.id,
                    kategoriaId = kategoria?.id ?: 0,
                    nazov = aktivita.nazov,
                    farba = kategoria?.farba ?: "#000000",
                    vaha = aktivita.vaha,
                    date = today
                )
                if (aktivita.jednorazova) {
                    aktivitaViewModel.deleteAktivita(aktivita)
                }
            }) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = null,
                    tint = buttonColor,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        IconButton(
            onClick = {
                onDelete()
                offsetX = 0f
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset { IntOffset((200 + offsetX.roundToInt()).coerceAtLeast(0), 0) }
                .background(Color.Red)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ExpandableFloatingActionButton(navController: NavHostController) {//Tlačítko na zobrazenie pridania kategórie alebo aktivít
    var isExpanded by remember { mutableStateOf(false) }
    var padding = 2.dp
    if (isLandscape()) {
        padding = 30.dp
    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(horizontal = padding, vertical = padding)
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
    //Obrázky pre menu na navigáciu aplikáciou
    Statistics("Štatistiky", Icons.Default.DateRange),
    MainScreen("Domov", Icons.Default.Home),
    CategoryView("Kategórie", Icons.Default.List),
    Settings("Nastavenia", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(//Spodná časť obrazovky ktorá zobrazuje menu na navigáciu aplikáciou
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
fun TopSection(ulozeneViewModel: UlozeneView) {
    val uiState by ulozeneViewModel.uiState.collectAsState()
    //Zobrazovanie grafu ktorý ukazuje iba dáta z dneskajška
    LaunchedEffect(Unit) {
        val today = Date()
        val startDate = Calendar.getInstance().apply {
            time = today
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val endDate = Calendar.getInstance().apply {
            time = today
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.time
        ulozeneViewModel.getUlozeneByDateRange(startDate, endDate)
    }

    val pieChartData =
        if (uiState.ulozene.isNotEmpty()) {//Špecifikácia dát pre zobrazenie koláčovým grafom
            PieChartData(
                slices = uiState.ulozene.groupBy { it.kategoriaId }.map { (kategoriaId, ulozene) ->
                    val totalWeight = ulozene.sumOf { it.vaha }
                    PieChartData.Slice(
                        value = totalWeight.toFloat(),
                        color = parseColor(ulozene.first().farba)
                    )
                }
            )
        } else {
            PieChartData(
                slices = listOf(
                    PieChartData.Slice(value = 1f, color = Color.Gray)
                )
            )
        }

    // Box komponent pre zobrazenie koláčového grafu
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7))
            .padding(30.dp)
    ) {
        PieChart( //Zobrazenie koláčového grafu
            pieChartData = pieChartData,
            modifier = Modifier.size(200.dp),
            sliceDrawer = SimpleSliceDrawer()
        )
    }
}
