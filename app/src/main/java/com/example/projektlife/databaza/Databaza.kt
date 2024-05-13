package com.example.projektlife.databaza

import KategoriaDao
import com.example.projektlife.dataclass.Kategoria
import android.content.Context
import androidx.room.*

@Database(entities = [Kategoria::class], version = 1)
abstract class Databaza : RoomDatabase() {
    abstract fun kategoriaDao(): KategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: Databaza? = null

        fun getDatabase(context: Context): Databaza {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Databaza::class.java,
                    "dtb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}