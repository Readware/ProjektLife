package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projektlife.viewmodel.KategoriaView
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.enumerator.Typ
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun KategoriaScreen(navController: NavHostController, kategoriaViewModel: KategoriaView) {
    var nazov by remember { mutableStateOf("") }
    var selectedTyp by remember { mutableStateOf(Typ.POSITIVNA) }
    kategoriaViewModel.getAllKategorie()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 50.dp, start = 10.dp, end = 10.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Row {
            Typ.values().forEach { type ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = type == selectedTyp,
                        onClick = { selectedTyp = type }
                    )
                    Text(text = type.name.capitalize())
                }
            }
        }
        val vybrataColor: MutableState<Color> = remember { mutableStateOf(Color.Red) }
        Text("Vyberte farbu:")
        ColorPicker(selectedColor = vybrataColor)

        Spacer(modifier = Modifier.height(20.dp))
        Text("Typ kategórie: ${selectedTyp.name}")
        Text("Názov kategórie: $nazov")
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Black)
                .background(color = vybrataColor.value)
        )
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
            kategoriaViewModel.addKategoria(
                Kategoria(
                    typ =  selectedTyp.toString(),
                    nazov = nazov,
                    farba = vybrataColor.value.toString()
                )
            )
        }
            navController.navigate("main_screen")
        }) {
            Text("Uložiť kategóriu")
        }
    }
}
@Composable
fun ColorPicker(selectedColor: MutableState<Color>) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)
    val scrollState = rememberScrollState()

    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .border(
                        width = if (selectedColor.value == color) 2.dp else 0.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .background(color = color, shape = CircleShape)
                    .clickable { selectedColor.value = color }
            )
        }
    }
}