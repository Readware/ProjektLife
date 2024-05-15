package com.example.projektlife.obrazovky.ui

import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.R
import com.example.projektlife.viewmodel.UlozeneView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    navController: NavHostController,
    ulozeneView: UlozeneView = viewModel()
) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.Statistics) }
    var startDate by remember { mutableStateOf(getStartOfMonth()) }
    var endDate by remember { mutableStateOf(getEndOfMonth()) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    LaunchedEffect(Unit) {
        ulozeneView.getUlozeneByDateRange(startDate, endDate)
    }

    Scaffold(
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
        if (isLandscape()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Štatistika", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        DatePicker(
                            label = "Počiatočný dátum",
                            selectedDate = startDate,
                            onDateSelected = { newStartDate ->
                                startDate = newStartDate
                                ulozeneView.getUlozeneByDateRange(startDate, endDate)
                            }
                        )
                        DatePicker(
                            label = "Konečný dátum",
                            selectedDate = endDate,
                            onDateSelected = { newEndDate ->
                                endDate = newEndDate
                                ulozeneView.getUlozeneByDateRange(startDate, endDate)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    CategoryList(ulozeneView = ulozeneView, selectedDate = selectedDate)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    LineChart(
                        ulozeneView = ulozeneView,
                        onDateSelected = { date ->
                            selectedDate = date
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text("Štatistika", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                LineChart(
                    ulozeneView = ulozeneView,
                    onDateSelected = { date ->
                        selectedDate = date
                    }
                )
                Row {
                    DatePicker(
                        label = "Počiatočný dátum",
                        selectedDate = startDate,
                        onDateSelected = { newStartDate ->
                            startDate = newStartDate
                            ulozeneView.getUlozeneByDateRange(startDate, endDate)
                        }
                    )
                    DatePicker(
                        label = "Konečný dátum",
                        selectedDate = endDate,
                        onDateSelected = { newEndDate ->
                            endDate = newEndDate
                            ulozeneView.getUlozeneByDateRange(startDate, endDate)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CategoryList(ulozeneView = ulozeneView, selectedDate = selectedDate)
            }
        }
    }
}

@Composable
fun DatePicker(label: String, selectedDate: Date, onDateSelected: (Date) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = selectedDate }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            onDateSelected(calendar.time)
        }, year, month, day
    )

    Column {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { datePickerDialog.show() }) {
            Text(text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate))
        }
    }
}

@Composable
fun LineChart(
    ulozeneView: UlozeneView,
    onDateSelected: (Date) -> Unit
) {
    val uiState by ulozeneView.uiState.collectAsState()
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            LineChart(ctx).apply {
                description.isEnabled = false
                setDrawGridBackground(false)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = object : ValueFormatter() {
                    private val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
                    override fun getFormattedValue(value: Float): String {
                        return dateFormat.format(Date(value.toLong()))
                    }
                }
                xAxis.granularity = 1f
                axisLeft.granularity = 1f
            }
        },
        update = { chart ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val aggregatedData = uiState.ulozene.groupBy { it.nazov to it.farba }
                .mapValues { (_, values) ->
                    values.groupBy { it.date }
                        .mapValues { (_, dateValues) -> dateValues.sumOf { it.vaha } }
                }


            val dataSets = aggregatedData.map { (key, dateMap) ->
                val entries = dateMap.mapNotNull { (dateStr, value) ->
                    try {
                        val date = dateFormat.parse(dateStr)
                        date?.time?.let { timestamp -> Entry(timestamp.toFloat(), value.toFloat()) }
                    } catch (e: Exception) {
                        null
                    }
                }

                LineDataSet(entries, key.first).apply {
                    color = parseColor(key.second).toArgb()
                    valueTextColor = ContextCompat.getColor(context, R.color.black)
                }
            }

            chart.data = LineData(dataSets)
            chart.invalidate()

            chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    e?.let {
                        val selectedDate = Date(it.x.toLong())
                        if (uiState.ulozene.any { ulozene -> ulozene.date == dateFormat.format(selectedDate) }) {
                            onDateSelected(selectedDate)
                        }
                    }
                }

                override fun onNothingSelected() {}
            })
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    )
}

@Composable
fun CategoryList(ulozeneView: UlozeneView, selectedDate: Date?) {
    val ulozeneUiState by ulozeneView.uiState.collectAsState()

    val filteredUlozene = if (selectedDate != null) {
        ulozeneUiState.ulozene.filter {
            it.date == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
        }
    } else {
        ulozeneUiState.ulozene
    }
    val aggregatedData = filteredUlozene.groupBy { it.nazov to it.farba }
        .mapValues { (_, values) ->
            values.sumOf { it.vaha }
        }

    LazyColumn {
        items(aggregatedData.entries.toList()) { (key, totalWeight) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Canvas(
                    modifier = Modifier.size(24.dp),
                    onDraw = {
                        drawCircle(color = parseColor(key.second))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = key.first, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Váha: $totalWeight")
            }
        }
    }
}

fun getStartOfMonth(): Date {
    return Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun getEndOfMonth(): Date {
    return Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.time
}


@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
