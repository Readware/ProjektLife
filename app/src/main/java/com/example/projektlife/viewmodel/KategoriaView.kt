package com.example.projektlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.repository.KategorieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KategoriaView(private val database: KategorieRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(KategoriaUiState(kategorie = listOf()))
    val uiState: StateFlow<KategoriaUiState> = _uiState.asStateFlow()

    fun addKategoria(kategoria: Kategoria) {
        viewModelScope.launch {
            database.insertAll(kategoria)
            getAllKategorias()
        }
    }

    fun updateKategoria(kategoria: Kategoria) {
        viewModelScope.launch {
            database.updateKategoria(kategoria)
        }
    }

    fun deleteKategoria(kategoria: Kategoria) {
        viewModelScope.launch {
            database.deleteKategoria(kategoria)
        }
    }

    fun deleteAllKategorias() {
        viewModelScope.launch {
            database.deleteAll()
        }
    }

    fun getAllKategorias() {
        viewModelScope.launch {
            val tmp_kategorie = database.getAllKategorias()
            _uiState.update { it.copy(kategorie = tmp_kategorie) }
        }
    }
}

data class KategoriaUiState(
    val kategorie: List<Kategoria>
)
