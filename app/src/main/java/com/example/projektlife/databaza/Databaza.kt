package com.example.projektlife.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.dataclass.Ulozene

@Database(entities = [Aktivita::class, Kategoria::class, Ulozene::class], version = 1)
abstract class Databaza : RoomDatabase() {
    abstract fun kategoriaDao(): KategoriaDao
    abstract fun aktivitaDao(): AktivitaDao
    abstract fun ulozeneDao(): UlozeneDao

    companion object {
        @Volatile
        private var INSTANCE: Databaza? = null

        fun getDatabase(context: Context): Databaza {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Databaza::class.java,
                    "projektlife_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
