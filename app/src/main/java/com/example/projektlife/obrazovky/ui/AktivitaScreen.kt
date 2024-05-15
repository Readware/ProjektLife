package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView

@Composable
fun AktivitaScreen(
    navController: NavHostController,
    kategoriaView: KategoriaView = viewModel(),
    aktivitaView: AktivitaView = viewModel()
) {
    // Získavame stav kategórií z viewModelu
    val uiState by kategoriaView.uiState.collectAsState()

    // Ukladáme si vybratú kategóriu, názov, váhu a boolean pre jednorazovú aktivitu
    var selectedKategoria by rememberSaveable(stateSaver = kategoriaSaver) {
        mutableStateOf<Kategoria?>(
            null
        )
    }
    var nazov by rememberSaveable { mutableStateOf("") }
    var vaha by rememberSaveable { mutableStateOf("5") }
    var jednorazova by rememberSaveable { mutableStateOf(false) }

    // Načítavame všetky kategórie
    kategoriaView.getAllKategorie()

    if (isLandscape()) {
        // Ak je zariadenie v landscape móde
        Row(
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
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
                    onOptionSelected = { selectedKategoria = it },
                    navController = navController
                )
                OutlinedTextField(
                    value = nazov,
                    onValueChange = { nazov = it },
                    label = { Text("Nazov") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = vaha,
                    onValueChange = { vaha = it },
                    label = { Text("Váha (odporúčané od 1 - 10)") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(checked = jednorazova, onCheckedChange = { jednorazova = it })
                    Text("Jednorázová aktivita?")
                }
                Button(
                    onClick = {
                        selectedKategoria?.let { kategoria ->
                            val vahaInt = vaha.toIntOrNull() ?: 5
                            aktivitaView.addAktivita(nazov, kategoria.id, vahaInt, jednorazova)
                            navController.navigate("main_screen")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) {
                    Text("Vytvor Aktivitu")
                }
            }
        }
    } else {
        // Ak je zariadenie v portrait móde
        LazyColumn(
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "Vytvorte Aktivitu: ",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
                DropdownMenuBox(
                    options = uiState.kategorie,
                    selectedOption = selectedKategoria,
                    onOptionSelected = { selectedKategoria = it },
                    navController = navController
                )
                OutlinedTextField(
                    value = nazov,
                    onValueChange = { nazov = it },
                    label = { Text("Nazov") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = vaha,
                    onValueChange = { vaha = it },
                    label = { Text("Váha (odporúčané od 1 - 10)") },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(checked = jednorazova, onCheckedChange = { jednorazova = it })
                    Text("Jednorázová aktivita?")
                }
                Button(
                    onClick = {
                        selectedKategoria?.let { kategoria ->
                            val vahaInt = vaha.toIntOrNull() ?: 5
                            aktivitaView.addAktivita(nazov, kategoria.id, vahaInt, jednorazova)
                            navController.navigate("main_screen")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Vytvor Aktivitu")
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(//Funkcia ktorá vytvorí vysúvacie menu pre vyber kategórie po kliknutí na tlačidlo
    options: List<Kategoria>,
    selectedOption: Kategoria?,
    onOptionSelected: (Kategoria) -> Unit,
    navController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        val buttonText = if (options.isEmpty()) "Vytvorte kategóriu" else (selectedOption?.nazov
            ?: "Vyberte kategóriu")//v prípade že neexistujú kategórie prejde do obrazovky kde vytvorí užívateľ novú
        Button(
            onClick = {
                if (options.isEmpty()) {
                    navController.navigate("add_kategoria")
                } else {
                    expanded = true
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = selectedOption?.let {
                parseColor(
                    it.farba
                )
            }
                ?: Color.Gray),//použije funkciu vytvorenú pre parsovanie farby, ak nevyjde premena farba bude sivá
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(buttonText)
        }
        DropdownMenu(//vysúvacie menu
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(//jeho prvky
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier.background(parseColor(option.farba)),
                    text = { Text(text = option.nazov, color = Color.Black) }
                )
            }
        }
    }
}

// Funkcia na parsovanie farby z reťazca na objekt Color
fun parseColor(colorString: String): Color {
    return try {
        val rgba = colorString
            .removePrefix("Color(")
            .removeSuffix(", sRGB IEC61966-2.1)")
            .split(", ")
            .map { it.toFloatOrNull() ?: 0f }
        if (rgba.size == 4) {//pole rgba musí mať presne 4 prvky inak nieje možné ho použiť, ak by náhodou stále niečo nevyšlo vráti šedú farbu
            Color(rgba[0], rgba[1], rgba[2], rgba[3])
        } else {
            Color.Unspecified //Vráti neznámu, farbu ak je vzorec zlý
        }
    } catch (e: Exception) {
        Color.Gray
    }
}

// Saver objekt pre ukladanie a obnovu stavu objektu Kategoria
val kategoriaSaver = Saver<Kategoria?, Map<String, String>>(
    save = { //Rozkladanie kategórie pre uloženie
        it?.let { category ->
            mapOf(
                "id" to category.id.toString(),
                "nazov" to category.nazov,
                "farba" to category.farba,
                "typ" to category.typ
            )
        } ?: emptyMap()
    },
    restore = { state ->    //Skladanie kategórie naspať do pôvodného stavu
        state["id"]?.toIntOrNull()?.let { id ->
            Kategoria(
                id = id,
                nazov = state["nazov"] ?: "",
                farba = state["farba"] ?: "",
                typ = state["typ"] ?: ""
            )
        }
    }
)
