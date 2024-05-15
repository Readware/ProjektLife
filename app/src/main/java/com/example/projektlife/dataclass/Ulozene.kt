package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ulozene")
data class Ulozene(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primárny kľúč s automatickým generovaním hodnôt
    val aktivitaId: Int, // ID aktivity, odkaz na tabuľku aktivita
    val kategoriaId: Int, // ID kategórie, odkaz na tabuľku kategoria
    val nazov: String, // Názov uloženého záznamu
    val farba: String, // Farba, podľa kategórie
    val vaha: Int, // Váha podľa aktivity
    val date: String // Dátum uloženia záznamu, uložený ako text
)
