package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.enumerator.Typ
import com.example.projektlife.viewmodel.KategoriaView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun KategoriaScreen(navController: NavHostController, kategoriaViewModel: KategoriaView) {
    var nazov by rememberSaveable { mutableStateOf("") } // Ukladanie a obnovovanie stavu pre názov kategórie
    var selectedTyp by rememberSaveable { mutableStateOf(Typ.POSITIVNA) } // Ukladanie a obnovovanie stavu pre typ kategórie
    val isLandscape = isLandscape() // Skontroluje, či je zariadenie v landscape móde

    // Vytvorenie vlastného Saver pre farbu
    val colorSaver = Saver<Color, Int>(
        save = { it.toArgb() }, // Uloženie farby ako integer hodnoty
        restore = { Color(it) } // Obnovenie farby z integer hodnoty
    )

    // Použitie rememberSaveable na uloženie a obnovenie stavu farby
    var vybrataColor by rememberSaveable(stateSaver = colorSaver) { mutableStateOf(Color.Red) }
    val vybrataColorState = remember { mutableStateOf(vybrataColor) }

    // Aktualizácia skutočnej farby, keď sa zmení stav farby
    vybrataColor = vybrataColorState.value

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Povolenie vertikálneho skrolovania
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Vytvoriť novú kategóriu", style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = nazov,
                    onValueChange = { nazov = it },
                    label = { Text("Zadajte názov kategórie") },
                    placeholder = { Text("Zadajte názov kategórie", color = Color.Gray) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Typ.entries.forEach { type ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = type == selectedTyp,
                                onClick = { selectedTyp = type }
                            )
                            Text(text = type.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("Vyberte farbu:")
                // Komponent na výber farby
                VyberFarbu(selectedColor = vybrataColorState)
                Spacer(modifier = Modifier.height(20.dp))
                Text("Typ kategórie: ${selectedTyp.name}")
                Text("Názov kategórie: $nazov")
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, Color.Black)
                        .background(color = vybrataColorState.value) // Nastavenie pozadia s vybranou farbou
                )
                Row {
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            kategoriaViewModel.addKategoria(
                                Kategoria(
                                    typ = selectedTyp.toString(),
                                    nazov = nazov,
                                    farba = vybrataColor.toString()
                                )
                            )
                        }
                        navController.popBackStack() // Navigácia späť po uložení
                    }) {
                        Text("Uložiť kategóriu")
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 50.dp)
                .verticalScroll(rememberScrollState()), // Povolenie vertikálneho skrolovania
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Vytvoriť novú kategóriu", style = MaterialTheme.typography.h4)
            TextField(
                value = nazov,
                onValueChange = { nazov = it },
                label = { Text("Zadajte názov kategórie") },
                placeholder = { Text("Zadajte názov kategórie", color = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            Row {
                Typ.values().forEach { type ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = type == selectedTyp,
                            onClick = { selectedTyp = type }
                        )
                        Text(text = type.name)
                    }
                }
            }
            Text("Vyberte farbu:")
            // Komponent na výber farby
            VyberFarbu(selectedColor = vybrataColorState)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Typ kategórie: ${selectedTyp.name}")
            Text("Názov kategórie: $nazov")
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, Color.Black)
                    .background(color = vybrataColorState.value) // Nastavenie pozadia s vybranou farbou
            )
            Button(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    kategoriaViewModel.addKategoria(
                        Kategoria(
                            typ = selectedTyp.toString(),
                            nazov = nazov,
                            farba = vybrataColor.toString()
                        )
                    )
                }
                navController.navigate("main_screen") // Navigácia na hlavnú obrazovku po uložení
            }) {
                Text("Uložiť kategóriu")
            }
        }
    }
}

