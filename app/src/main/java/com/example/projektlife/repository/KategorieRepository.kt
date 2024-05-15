package com.example.projektlife.repository

import com.example.projektlife.databaza.KategoriaDao
import com.example.projektlife.dataclass.Kategoria

class KategorieRepository(private val kategoriaDao: KategoriaDao) {

    suspend fun getAllKategorie(): List<Kategoria> {
        return kategoriaDao.getAllKategorie()
    }

    suspend fun getKategoriaById(id: Int): Kategoria? {
        return kategoriaDao.getKategoriaById(id)
    }

    suspend fun insertAll(vararg kategorie: Kategoria) {
        kategoriaDao.insertAll(*kategorie)
    }

    suspend fun updateKategoria(kategoria: Kategoria) {
        kategoriaDao.updateKategoria(kategoria)
    }

    suspend fun deleteKategoria(kategoria: Kategoria) {
        kategoriaDao.deleteKategoria(kategoria)
    }

    suspend fun deleteAll() {
        kategoriaDao.deleteAll()
    }
}
