package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategoria")
data class Kategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nazov: String,
    val farba: String,
    val typ: String // "positivna", "negativna", "neutralna"
)
