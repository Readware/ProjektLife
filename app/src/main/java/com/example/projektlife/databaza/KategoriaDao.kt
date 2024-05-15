package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projektlife.dataclass.Kategoria

@Dao
interface KategoriaDao {
    @Query("SELECT * FROM Kategoria")
    suspend fun getAllKategorie(): List<Kategoria>

    @Query("SELECT * FROM Kategoria WHERE id = :id")
    suspend fun getKategoriaById(id: Int): Kategoria?

    @Insert
    suspend fun insertAll(vararg kategorie: Kategoria)

    @Update
    suspend fun updateKategoria(kategoria: Kategoria)

    @Delete
    suspend fun deleteKategoria(kategoria: Kategoria)

    @Query("DELETE FROM Kategoria")
    suspend fun deleteAll()
}
