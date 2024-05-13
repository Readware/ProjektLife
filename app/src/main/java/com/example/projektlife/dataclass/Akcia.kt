package com.example.projektlife.dataclass

data class Akcia(
    val id: Int,
    val nazov: String,
    val vaha: Int,
   // val kategoria: com.example.projektlife.dataclass.Kategoria,
    val jednorazova: Boolean
)
