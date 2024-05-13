package com.example.projektlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.repository.KategorieRepository

class KategoriaView(private val database: KategorieRepository) : ViewModel() {
    fun addKategoria(kategoria: Kategoria) {
        viewModelScope.launch() {
            database.insertAll(kategoria)
        }
    }

    fun getAllKategorie() {
        viewModelScope.launch() {
            val kategorie = database.getAll()
        }
    }
}
