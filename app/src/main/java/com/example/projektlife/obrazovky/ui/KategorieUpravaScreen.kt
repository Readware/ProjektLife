package com.example.projektlife.obrazovky.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.viewmodel.KategoriaView

@Composable
fun KategorieUpravaScreen(navController: NavHostController, kategoriaView: KategoriaView = viewModel()) {
    val uiState by kategoriaView.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(BottomNavItem.CategoryView) }
    kategoriaView.getAllKategorie()

    if (showDialog) {
        DialogVymazatKategoriu(
            onConfirm = {
                kategoriaView.deleteAllKategorie()
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = selectedItem, onItemSelected = { item ->
                selectedItem = item
                when (item) {
                    BottomNavItem.Statistics -> navController.navigate("statistics")
                    BottomNavItem.MainScreen -> navController.navigate("main_screen")
                    BottomNavItem.CategoryView -> navController.navigate("kategorie_uprava")
                    BottomNavItem.Settings -> navController.navigate("nastavenia")
                }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .padding(innerPadding)
        ) {
            if(isLandscape()){
                Row{
                Text(text = "Kategórie", style = androidx.compose.material.MaterialTheme.typography.h4)
                    Button(onClick = { showDialog = true }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                        Text(text = "Vymazať všetky kategórie")
                    }
                }
            }else{
            Text(text = "Kategórie", style = androidx.compose.material.MaterialTheme.typography.h4)
            Text("Kliknutím upravíte kategóriu a posunutím ju môžte vymazať")
            Button(onClick = { showDialog = true }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "Vymazať všetky kategórie")
            }}
            LazyColumn {
                items(uiState.kategorie) { kategoria ->
                    SwipeableKategoriaItem(
                        kategoria = kategoria,
                        onDelete = { kategoriaView.deleteKategoria(kategoria) },
                        onClick = { navController.navigate("kategoria_edit/${kategoria.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun SwipeableKategoriaItem(kategoria: Kategoria, onDelete: () -> Unit, onClick: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                        offsetX = offsetX.coerceIn(-200f, 0f)
                    },
                    onDragEnd = {
                        if (offsetX < -100f) {
                            onDelete()
                        } else {
                            offsetX = 0f
                        }
                    }
                )
            }
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .offset(x = offsetX.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(parseColor(kategoria.farba), CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = kategoria.nazov, style = MaterialTheme.typography.bodySmall)
        }
        if (offsetX < -100f) {
            Text(
                text = "Vymazať",
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }
    }
}
@Composable
fun DialogVymazatKategoriu(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Vymazanie všetkých kategórií") },
        text = { Text(text = "Ste si istý že chcete vymazať všetky kategórie?") },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = onConfirm) {
                Text("Áno chcem")
            }
        },
        dismissButton = {
            androidx.compose.material.TextButton(onClick = onDismiss) {
                Text("Nie nechcem")
            }
        }
    )
}


