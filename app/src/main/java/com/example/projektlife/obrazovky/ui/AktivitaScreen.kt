package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView

@Composable
fun AktivitaScreen(navController: NavHostController, kategoriaView: KategoriaView = viewModel(), aktivitaView: AktivitaView = viewModel()) {
    val uiState by kategoriaView.uiState.collectAsState()
    var selectedKategoria by remember { mutableStateOf<Kategoria?>(null) }
    var nazov by remember { mutableStateOf("") }
    var jednorazova by remember { mutableStateOf(false) }
    kategoriaView.getAllKategorias()

    Column(modifier = Modifier
        .padding(top = 30.dp, start = 16.dp, end = 16.dp)
        .fillMaxSize()) {
        Text(
            text = "Vytvorte Aktivitu: ",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        DropdownMenuBox(
            options = uiState.kategorie,
            selectedOption = selectedKategoria,
            onOptionSelected = { selectedKategoria = it }
        )
        OutlinedTextField(
            value = nazov,
            onValueChange = { nazov = it },
            label = { Text("Nazov") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = jednorazova, onCheckedChange = { jednorazova = it })
            Text("Jednorázová aktivita?")
        }

        Button(onClick = {
            selectedKategoria?.let { kategoria ->
                aktivitaView.addAktivita(nazov, kategoria.id, jednorazova)
                navController.navigate("main_screen")
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start))
        { Text("Vytvor Aktivitu") }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<Kategoria>,
    selectedOption: Kategoria?,
    onOptionSelected: (Kategoria) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = selectedOption?.let { parseColor(it.farba) } ?: Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(selectedOption?.nazov ?: "Vyberte kategóriu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }, modifier = Modifier.background(parseColor(option.farba)), text = { Text(text = option.nazov, color = Color.Black) })
            }
        }
    }
}

fun parseColor(colorString: String): Color {
    val rgba = colorString
        .removePrefix("Color(")
        .removeSuffix(", sRGB IEC61966-2.1)")
        .split(", ")
        .map { it.toFloat() }
    return if (rgba.size == 4) {
        Color(rgba[0], rgba[1], rgba[2], rgba[3])
    } else {
        Color.Unspecified
    }
}