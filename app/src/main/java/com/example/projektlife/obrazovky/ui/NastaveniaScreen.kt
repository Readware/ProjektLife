package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 16.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            kategoriaView.deleteAllKategorias()
            aktivitaView.deleteAllAktivitas()
            ulozeneView.deleteAllHistory()
        }) {
            Text("Delete All Data")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            ulozeneView.deleteAllHistory()
        }) {
            Text("Delete History")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            kategoriaView.deleteAllKategorias()
        }) {
            Text("Delete All Categories")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            aktivitaView.deleteAllAktivitas()
        }) {
            Text("Delete All Activities")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Notifications", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
