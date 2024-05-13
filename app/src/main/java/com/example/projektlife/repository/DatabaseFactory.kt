package com.example.projektlife.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projektlife.viewmodel.KategoriaView

class DatabaseFactory(private val kategorieRepository: KategorieRepository)
    : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(KategoriaView::class.java) -> {
                KategoriaView(kategorieRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}
