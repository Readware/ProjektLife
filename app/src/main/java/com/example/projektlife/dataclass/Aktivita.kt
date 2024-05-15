package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// Definuje entitu "Aktivita" pre databázu Room s názvom tabuľky "aktivita"
// Táto entita má cudzí kľúč (ForeignKey) na entitu Kategoria
@Entity(
    tableName = "aktivita",
    foreignKeys = [ForeignKey(
        entity = Kategoria::class, // Referencia na entitu Kategoria
        parentColumns = ["id"], // Stĺpec v entite Kategoria, ktorý je zahraničným kľúčom
        childColumns = ["kategoriaId"], // Stĺpec v entite Aktivita, ktorý referencuje zahraničný kľúč
        onDelete = ForeignKey.CASCADE // Ak sa záznam v Kategoria zmaže, zmažú sa aj príslušné záznamy v Aktivita
    )]
)
data class Aktivita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primárny kľúč s automatickým generovaním hodnôt
    val nazov: String, // Názov aktivity
    val kategoriaId: Int, // ID kategórie, referencuje entitu Kategoria
    val vaha: Int, // Váha aktivity, ktorá symbolizuje jej vážnosť v grafe
    val jednorazova: Boolean // Indikuje, či je aktivita jednorazová
)
