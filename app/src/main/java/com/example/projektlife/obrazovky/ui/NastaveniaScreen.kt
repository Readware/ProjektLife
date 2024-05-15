package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
    var selectedItem by remember { mutableStateOf(BottomNavItem.Settings) }

    Scaffold(
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
                .padding(vertical = 30.dp, horizontal = 16.dp)
        ) {
            Text(text = "Nastavenia", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                kategoriaView.deleteAllKategorias()
                aktivitaView.deleteAllAktivitas()
                ulozeneView.deleteAllHistory()
            }) {
                Text("Vymazať všetky dáta")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                ulozeneView.deleteAllHistory()
            }) {
                Text("Vymazať históriu")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                kategoriaView.deleteAllKategorias()
            }) {
                Text("Vymazať všetky kategórie")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                aktivitaView.deleteAllAktivitas()
            }) {
                Text("Vymazať všetky aktivity")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Vrátiť sa")
            }
        }
    }
}
