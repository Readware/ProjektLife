package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projektlife.dataclass.Kategoria

@Dao
interface KategoriaDao {
    @Query("SELECT * FROM Kategoria")
    suspend fun getAll(): List<Kategoria>

    @Insert
    suspend fun insertAll(kategorias: Kategoria)
}
