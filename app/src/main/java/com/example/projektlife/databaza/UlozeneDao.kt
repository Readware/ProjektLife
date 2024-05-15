package com.example.projektlife.databaza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projektlife.dataclass.Ulozene

@Dao
interface UlozeneDao {
    @Query("SELECT * FROM ulozene")
    suspend fun getAllUlozene(): List<Ulozene>

    @Query("SELECT * FROM ulozene WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getUlozeneByDateRange(startDate: String, endDate: String): List<Ulozene>

    @Insert
    suspend fun insertAll(vararg ulozene: Ulozene)
    @Query("DELETE FROM ulozene")
    suspend fun deleteAll()
}
