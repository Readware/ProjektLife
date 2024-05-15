package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projektlife.dataclass.Aktivita

@Dao
interface AktivitaDao {
    // Vráti zoznam všetkých aktivít z databázy
    @Query("SELECT * FROM aktivita")
    suspend fun getAllAktivitas(): List<Aktivita>

    // Vráti aktivitu podľa jej ID
    @Query("SELECT * FROM aktivita WHERE id = :id")
    suspend fun getAktivitaById(id: Int): Aktivita?

    // Vloží jednu alebo viac aktivít do databázy
    @Insert
    suspend fun insertAll(vararg aktivita: Aktivita)

    // Aktualizuje existujúcu aktivitu v databáze
    @Update
    suspend fun updateAktivita(aktivita: Aktivita)

    // Vymaže aktivitu z databázy
    @Delete
    suspend fun deleteAktivita(aktivita: Aktivita)

    // Vymaže všetky aktivity z databázy
    @Query("DELETE FROM aktivita")
    suspend fun deleteAll()
}
