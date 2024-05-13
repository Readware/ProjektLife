package com.example.projektlife.databaza

import Kategoria
import androidx.room.*

@Dao
interface KategoriaDao {
    @Query("SELECT * FROM Kategoria")
    fun getAll(): List<Kategoria>
    @Insert
    suspend fun insertAll(vararg kategorias: Kategoria)
    @Delete
    suspend fun delete(kategoria: Kategoria)
}
