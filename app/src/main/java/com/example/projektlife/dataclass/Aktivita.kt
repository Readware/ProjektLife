package com.example.projektlife.dataclass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "aktivita",
    foreignKeys = [ForeignKey(
        entity = Kategoria::class,
        parentColumns = ["id"],
        childColumns = ["kategoriaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Aktivita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nazov: String,
    val kategoriaId: Int,
    val vaha: Int,
    val jednorazova: Boolean
)