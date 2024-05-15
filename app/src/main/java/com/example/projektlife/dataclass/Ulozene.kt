package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ulozene")
data class Ulozene(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val aktivitaId: Int,
    val kategoriaId: Int,
    val nazov: String,
    val farba: String,
    val vaha: Int,
    val date: String
)
