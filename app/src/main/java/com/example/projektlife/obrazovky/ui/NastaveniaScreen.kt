package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView
import com.example.projektlife.viewmodel.UlozeneView

@Composable
fun NastaveniaScreen(
    navController: NavHostController,
    kategoriaView: KategoriaView = viewModel(),
    aktivitaView: AktivitaView = viewModel(),
    ulozeneView: UlozeneView = viewModel()
) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.Settings) } // Udržiava aktuálne vybranú položku v spodnej navigačnej lište
    val isLandscape = isLandscape() // Určuje, či je zariadenie v landscape režime

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 30.dp, horizontal = 16.dp)
        ) {
            Text(
                text = "Nastavenia",
                style = MaterialTheme.typography.headlineMedium
            ) // Zobrazuje nadpis obrazovky

            Spacer(modifier = Modifier.height(16.dp)) // Pridáva medzeru medzi nadpis a obsah

            if (isLandscape) {
                // Rozloženie pre landscape režim
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            kategoriaView.deleteAllKategorie()
                            aktivitaView.deleteAllAktivitas()
                            ulozeneView.deleteAllHistory()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vymazať všetky dáta")
                    }

                    Button(
                        onClick = { ulozeneView.deleteAllHistory() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vymazať históriu")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Button(
                        onClick = { kategoriaView.deleteAllKategorie() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vymazať všetky kategórie")
                    }

                    Button(
                        onClick = { aktivitaView.deleteAllAktivitas() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vymazať všetky aktivity")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { navController.popBackStack() }) {
                    Text("Vrátiť sa")
                }

            } else {
                // Rozloženie pre portrait režim
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            kategoriaView.deleteAllKategorie()
                            aktivitaView.deleteAllAktivitas()
                            ulozeneView.deleteAllHistory()
                        }
                    ) {
                        Text("Vymazať všetky dáta")
                    }

                    Button(onClick = { ulozeneView.deleteAllHistory() }) {
                        Text("Vymazať históriu")
                    }

                    Button(onClick = { kategoriaView.deleteAllKategorie() }) {
                        Text("Vymazať všetky kategórie")
                    }

                    Button(onClick = { aktivitaView.deleteAllAktivitas() }) {
                        Text("Vymazať všetky aktivity")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { navController.popBackStack() }) {
                        Text("Vrátiť sa")
                    }
                }
            }
        }
    }
}
