package com.example.projektlife.repository

import com.example.projektlife.databaza.AktivitaDao
import com.example.projektlife.databaza.KategoriaDao
import com.example.projektlife.dataclass.Aktivita

class AktivitaRepository(
    private val aktivitaDao: AktivitaDao,
    private val kategoriaDao: KategoriaDao
) {

    suspend fun getAllAktivitas(): List<Aktivita> {
        return aktivitaDao.getAllAktivitas()
    }

    suspend fun insertAll(vararg aktivita: Aktivita) {
        aktivitaDao.insertAll(*aktivita)
    }

    suspend fun updateAktivita(aktivita: Aktivita) {
        aktivitaDao.updateAktivita(aktivita)
    }

    suspend fun deleteAktivita(aktivita: Aktivita) {
        aktivitaDao.deleteAktivita(aktivita)
    }

    suspend fun deleteAll() {
        aktivitaDao.deleteAll()
    }

    suspend fun getKategoriaById(kategoriaId: Int) = kategoriaDao.getKategoriaById(kategoriaId)
}
