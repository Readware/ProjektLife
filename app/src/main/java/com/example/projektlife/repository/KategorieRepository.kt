package com.example.projektlife.repository

import KategoriaDao
import androidx.room.Insert
import androidx.room.Query
import com.example.projektlife.dataclass.Kategoria

class KategorieRepository(private val kategoriaDao:KategoriaDao)
{

    suspend fun getAll() : List<Kategoria>{
        return kategoriaDao.getAll()
    }

    suspend fun insertAll(kategorie: Kategoria){
        kategoriaDao.insertAll(kategorie)
    }
}
