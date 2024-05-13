package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color
import com.example.projektlife.logika.PremienacFarby
import androidx.room.TypeConverters
import com.example.projektlife.enumerator.Typ
@Entity
data class Kategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val typ: Typ,
    val nazov: String,
    @TypeConverters(PremienacFarby::class) val farba: Color
)
