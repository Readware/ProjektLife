package com.example.projektlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.repository.KategorieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KategoriaView(private val database: KategorieRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(KategoriaUiState(kategorie = listOf()))
    val uiState: StateFlow<KategoriaUiState> = _uiState.asStateFlow()
    fun addKategoria(kategoria: Kategoria) {
        viewModelScope.launch() {
            database.insertAll(kategoria)
        }
    }

    fun getAllKategorie() {
        viewModelScope.launch() {
            val tmp_kategorie = database.getAll()
            _uiState.update { it.copy(kategorie = tmp_kategorie) }
        }
    }
}
