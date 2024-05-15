package com.example.projektlife.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView
import com.example.projektlife.viewmodel.UlozeneView

class DatabaseFactory(
    private val kategorieRepository: KategorieRepository,
    private val aktivitaRepository: AktivitaRepository,
    private val ulozeneRepository: UlozeneRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(KategoriaView::class.java) -> {
                KategoriaView(kategorieRepository) as T
            }
            modelClass.isAssignableFrom(AktivitaView::class.java) -> {
                AktivitaView(aktivitaRepository) as T
            }
            modelClass.isAssignableFrom(UlozeneView::class.java) -> {
                UlozeneView(ulozeneRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}