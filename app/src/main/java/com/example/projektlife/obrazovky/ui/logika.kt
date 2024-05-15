package com.example.projektlife.obrazovky.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

//Obsahuje logiku pre obrazovky
@Composable
fun isLandscape(): Boolean {//hovorí o tom či je mobil otočený horizontálne
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable//Vytvára menu pre výber farby
fun VyberFarbu(selectedColor: MutableState<Color>) {
    val colors =
        listOf(Color.Red, Color.Green, Color.Blue, Color(255, 165, 0), Color.Cyan, Color.Magenta)
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
                    .clickable { selectedColor.value = color } // Zmena farby pri kliknutí
            )
        }
    }
}

fun getStartOfMonth(): Date { //Ukáže začiatok terajšieho mesiaca
    return Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun getEndOfMonth(): Date {
    return Calendar.getInstance().apply {//Ukáže koniec terajšieho mesiaca
        set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.time
}

