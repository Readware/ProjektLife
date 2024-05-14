package com.example.projektlife.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView

class DatabaseFactory(
    private val kategorieRepository: KategorieRepository,
    private val aktivitaRepository: AktivitaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(KategoriaView::class.java) -> {
                KategoriaView(kategorieRepository) as T
            }
            modelClass.isAssignableFrom(AktivitaView::class.java) -> {
                AktivitaView(aktivitaRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}