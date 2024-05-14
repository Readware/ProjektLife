package com.example.projektlife.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria

@Database(entities = [Kategoria::class, Aktivita::class], version = 3, exportSchema = false)
abstract class Databaza : RoomDatabase() {
    abstract fun kategoriaDao(): KategoriaDao
    abstract fun aktivitaDao(): AktivitaDao

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
