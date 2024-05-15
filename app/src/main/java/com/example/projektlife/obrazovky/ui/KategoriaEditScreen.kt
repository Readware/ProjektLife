package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.viewmodel.KategoriaView

@Composable
fun KategoriaEditScreen(navController: NavHostController, kategoriaId: Int, kategoriaView: KategoriaView = viewModel()) {
    val uiState by kategoriaView.uiState.collectAsState()
    val kategoria = uiState.kategorie.find { it.id == kategoriaId }

    if (kategoria != null) {
        var nazov by remember { mutableStateOf(kategoria.nazov) }
        var farba by remember { mutableStateOf(kategoria.farba) }
        var selectedColor = remember { mutableStateOf(parseColor(farba)) }
        var typ by remember { mutableStateOf(kategoria.typ) }
        val typOptions = listOf("POSITIVNA", "NEGATIVNA", "NEUTRALNA")
        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 30.dp)
        ) {
            Text(text = "Upravenie Kategórie", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nazov,
                onValueChange = { nazov = it },
                label = { Text("Názov") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material.Text("Vyberte farbu:")
            VyberFarbu(selectedColor = selectedColor)
            Text(text = selectedColor.value.toString())
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
                kategoriaView.updateKategoria(kategoria.copy(nazov = nazov, farba = selectedColor.value.toString(), typ = typ))
                navController.popBackStack()
            }) {
                Text("Uložiť")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Zrušiť")
            }
        }
    } else {
        Text("Kategoria not found", style = MaterialTheme.typography.headlineMedium)
    }
}
