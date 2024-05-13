package com.example.projektlife.databaza

import ColorConverter
import Kategoria
import android.content.Context
import androidx.room.*

@Database(entities = [Kategoria::class], version = 1)
@TypeConverters(ColorConverter::class)
abstract class Databaza : RoomDatabase() {
    abstract fun kategoriaDao(): KategoriaDao

    companion object {
        @Volatile private var instance: Databaza? = null
        private val LOCK = Any()

        fun getInstance(context: Context): Databaza {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                Databaza::class.java, "kategoria-database")
                .build()
    }
}