package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projektlife.dataclass.Kategoria

@Dao
interface KategoriaDao {
    // Vráti zoznam všetkých kategórií z databázy
    @Query("SELECT * FROM Kategoria")
    suspend fun getAllKategorie(): List<Kategoria>

    // Vráti kategóriu podľa jej ID
    @Query("SELECT * FROM Kategoria WHERE id = :id")
    suspend fun getKategoriaById(id: Int): Kategoria?

    // Vloží jednu alebo viac kategórií do databázy
    @Insert
    suspend fun insertAll(vararg kategorie: Kategoria)

    // Aktualizuje existujúcu kategóriu v databáze
    @Update
    suspend fun updateKategoria(kategoria: Kategoria)

    // Vymaže kategóriu z databázy
    @Delete
    suspend fun deleteKategoria(kategoria: Kategoria)

    // Vymaže všetky kategórie z databázy
    @Query("DELETE FROM Kategoria")
    suspend fun deleteAll()
}
