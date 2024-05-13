package com.example.projektlife.dataclass
import androidx.compose.ui.graphics.Color

enum class Typ {
    POSITIVNA, NEGATIVNA, NEUTRAL
}
data class Kategoria(
    val id: Int,
    val typ: Typ,
    val nazov: String,
    val farba: Color
)
