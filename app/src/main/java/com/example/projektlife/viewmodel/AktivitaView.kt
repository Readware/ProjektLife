package com.example.projektlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.repository.AktivitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AktivitaView(private val aktivitaRepository: AktivitaRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow(AktivitaUiState(aktivity = listOf(), kategorie = emptyMap()))
    val uiState: StateFlow<AktivitaUiState> = _uiState.asStateFlow()

    init {
        getAllAktivitasWithKategorie()
    }

    fun addAktivita(nazov: String, kategoriaId: Int, vaha: Int, jednorazova: Boolean) {
        viewModelScope.launch {
            val aktivita = Aktivita(
                nazov = nazov,
                kategoriaId = kategoriaId,
                vaha = vaha,
                jednorazova = jednorazova
            )
            aktivitaRepository.insertAll(aktivita)
            getAllAktivitasWithKategorie()
        }
    }

    fun updateAktivita(aktivita: Aktivita) {
        viewModelScope.launch {
            aktivitaRepository.updateAktivita(aktivita)
        }
    }

    fun deleteAktivita(aktivita: Aktivita) {
        viewModelScope.launch {
            aktivitaRepository.deleteAktivita(aktivita)
            getAllAktivitasWithKategorie()
        }
    }

    fun deleteAllAktivitas() {
        viewModelScope.launch {
            aktivitaRepository.deleteAll()
            getAllAktivitasWithKategorie()
        }
    }

    fun getAllAktivitas() {
        viewModelScope.launch {
            getAllAktivitasWithKategorie()
        }
    }

    private fun getAllAktivitasWithKategorie() {
        viewModelScope.launch {
            val aktivity = aktivitaRepository.getAllAktivitas()
            val kategorie = aktivity.map { it.kategoriaId }.distinct()
                .associateWith { aktivitaRepository.getKategoriaById(it) }
            _uiState.update { it.copy(aktivity = aktivity, kategorie = kategorie) }
        }
    }
}

data class AktivitaUiState(
    val aktivity: List<Aktivita>,
    val kategorie: Map<Int, Kategoria?>
)
