package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategoria")
data class Kategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primárny kľúč s automatickým generovaním hodnôt
    val nazov: String, // Názov kategórie
    val farba: String, // Farba kategórie, uložená ako String, nech je možné ju spracovať room databázou bez potreby type converterov
    val typ: String // Typ kategórie: môže byť "POSITIVNA", "NEGATIVNA" alebo "NEUTRALNA"
)
