package com.example.projektlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektlife.dataclass.Ulozene
import com.example.projektlife.repository.UlozeneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UlozeneView(private val ulozeneRepository: UlozeneRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UlozeneUiState(ulozene = listOf()))
    val uiState: StateFlow<UlozeneUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun addUlozene(
        aktivitaId: Int,
        kategoriaId: Int,
        nazov: String,
        farba: String,
        vaha: Int,
        date: Date
    ) {
        viewModelScope.launch {
            val dateString = dateFormat.format(date)
            val ulozene = Ulozene(
                aktivitaId = aktivitaId,
                kategoriaId = kategoriaId,
                nazov = nazov,
                farba = farba,
                vaha = vaha,
                date = dateString
            )
            ulozeneRepository.insertAll(ulozene)
            getUlozeneByDateRange(date, date)
        }
    }

    fun getAllUlozene() {
        viewModelScope.launch {
            val ulozene = ulozeneRepository.getAllUlozene()
            _uiState.update { it.copy(ulozene = ulozene) }
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            ulozeneRepository.deleteAll()
        }
    }

    fun getUlozeneByDateRange(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            val startDateString = dateFormat.format(startDate)
            val endDateString = dateFormat.format(endDate)
            val ulozene = ulozeneRepository.getUlozeneByDateRange(startDateString, endDateString)
            _uiState.update { it.copy(ulozene = ulozene) }
        }
    }

    fun getUlozeneByDate(date: Date) {
        viewModelScope.launch {
            val DateString = dateFormat.format(date)
            val ulozeneList = ulozeneRepository.getUlozeneByDateRange(DateString, DateString)
            _uiState.value = _uiState.value.copy(ulozene = ulozeneList)
        }
    }
}

data class UlozeneUiState(
    val ulozene: List<Ulozene>
)
