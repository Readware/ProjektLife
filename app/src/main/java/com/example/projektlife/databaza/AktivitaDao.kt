package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projektlife.dataclass.Aktivita

@Dao
interface AktivitaDao {
    @Query("SELECT * FROM Aktivita")
    suspend fun getAllAktivitas(): List<Aktivita>

    @Insert
    suspend fun insertAll(vararg aktivita: Aktivita)

    @Update
    suspend fun updateAktivita(aktivita: Aktivita)

    @Delete
    suspend fun deleteAktivita(aktivita: Aktivita)

    @Query("DELETE FROM Aktivita")
    suspend fun deleteAll()
}
