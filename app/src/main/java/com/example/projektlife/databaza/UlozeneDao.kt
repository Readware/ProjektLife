package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projektlife.dataclass.Ulozene

@Dao
interface UlozeneDao {
    // Vráti zoznam všetkých uložených záznamov z databázy
    @Query("SELECT * FROM ulozene")
    suspend fun getAllUlozene(): List<Ulozene>

    // Vráti zoznam uložených záznamov z databázy v danom dátumovom rozsahu
    @Query("SELECT * FROM ulozene WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getUlozeneByDateRange(startDate: String, endDate: String): List<Ulozene>

    // Vloží jeden alebo viac uložených záznamov do databázy
    @Insert
    suspend fun insertAll(vararg ulozene: Ulozene)

    // Vymaže všetky uložené záznamy z databázy
    @Query("DELETE FROM ulozene")
    suspend fun deleteAll()
}
