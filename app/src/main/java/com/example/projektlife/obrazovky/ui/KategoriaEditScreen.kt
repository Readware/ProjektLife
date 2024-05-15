package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.viewmodel.KategoriaView


@Composable
fun KategoriaEditScreen(navController: NavHostController, kategoriaId: Int, kategoriaView: KategoriaView = viewModel()) {
    val uiState by kategoriaView.uiState.collectAsState()
    val kategoria = uiState.kategorie.find { it.id == kategoriaId }

    if (kategoria != null) {
        var nazov by rememberSaveable { mutableStateOf(kategoria.nazov) }
        var farba by rememberSaveable { mutableStateOf(kategoria.farba) }
        val colorSaver = Saver<Color, Int>(
            save = { it.toArgb() },
            restore = { Color(it) }
        )
        var selectedColor by rememberSaveable(stateSaver = colorSaver) { mutableStateOf(parseColor(farba)) }
        var typ by rememberSaveable { mutableStateOf(kategoria.typ) }
        val typOptions = listOf("POSITIVNA", "NEGATIVNA", "NEUTRALNA")
        var expanded by rememberSaveable { mutableStateOf(false) }

        val isLandscape = isLandscape()

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 30.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Upravenie Kategórie", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = nazov,
                        onValueChange = { nazov = it },
                        label = { Text("Názov") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(16.dp))

                    androidx.compose.material.Text("Vyberte farbu:")
                    VyberFarbu(selectedColor = remember { mutableStateOf(selectedColor) })
                    Spacer(modifier = Modifier.height(16.dp))

                    androidx.compose.material.Text("Vyberte typ:")
                    Box {
                        Button(onClick = { expanded = true }) {
                            Text(text = typ)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            typOptions.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    typ = option
                                    expanded = false
                                }) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        kategoriaView.updateKategoria(kategoria.copy(nazov = nazov, farba = selectedColor.toString(), typ = typ))
                        navController.popBackStack()
                    }) {
                        Text("Uložiť")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Zrušiť")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 30.dp)
                    .fillMaxSize()
            ) {
                item {
                    Text(text = "Upravenie Kategórie", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = nazov,
                        onValueChange = { nazov = it },
                        label = { Text("Názov") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(16.dp))

                    androidx.compose.material.Text("Vyberte farbu:")
                    VyberFarbu(selectedColor = remember { mutableStateOf(selectedColor) })
                    Spacer(modifier = Modifier.height(16.dp))

                    androidx.compose.material.Text("Vyberte typ:")
                    Box {
                        Button(onClick = { expanded = true }) {
                            Text(text = typ)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            typOptions.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    typ = option
                                    expanded = false
                                }) {
                                    Text(text = option)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        kategoriaView.updateKategoria(kategoria.copy(nazov = nazov, farba = selectedColor.toString(), typ = typ))
                        navController.popBackStack()
                    }) {
                        Text("Uložiť")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Zrušiť")
                    }
                }
            }
        }
    } else {
        navController.navigate("kategorie_uprava")
    }
}
