package com.example.projektlife.repository

import com.example.projektlife.databaza.UlozeneDao
import com.example.projektlife.dataclass.Ulozene

class UlozeneRepository(private val ulozeneDao: UlozeneDao) {
    suspend fun getAllUlozene(): List<Ulozene> {
        return ulozeneDao.getAllUlozene()
    }

    suspend fun getUlozeneByDateRange(startDate: String, endDate: String): List<Ulozene> {
        return ulozeneDao.getUlozeneByDateRange(startDate, endDate)
    }

    suspend fun insertAll(vararg ulozene: Ulozene) {
        ulozeneDao.insertAll(*ulozene)
    }
    suspend fun deleteAll() {
        ulozeneDao.deleteAll()
    }
}
