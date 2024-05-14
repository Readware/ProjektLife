package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun KategorieUkazka(navController: NavHostController , kategoriaView: KategoriaView = viewModel()) {
    val uiState by kategoriaView.uiState.collectAsState()
    LazyColumn {
        items(uiState.kategorie) { kategoria ->
            SwipeableKategoriaItem(
                kategoria = kategoria,
                onDelete = { }
            )
        }
    }
}


@Composable
fun SwipeableKategoriaItem(kategoria: Kategoria, onDelete: () -> Unit) {
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
                text = "Delete",
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
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
