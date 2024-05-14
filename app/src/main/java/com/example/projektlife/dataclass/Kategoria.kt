package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color

import androidx.room.TypeConverters
import com.example.projektlife.enumerator.Typ
@Entity(tableName = "kategoria")
data class Kategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val typ: String,
    val nazov: String,
    val farba: String
)
