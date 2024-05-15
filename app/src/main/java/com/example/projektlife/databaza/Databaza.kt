package com.example.projektlife.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projektlife.dataclass.Aktivita
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.dataclass.Ulozene

// Definuje databázu Room so subjektmi Aktivita, Kategoria a Ulozene
@Database(entities = [Aktivita::class, Kategoria::class, Ulozene::class], version = 1)
abstract class Databaza : RoomDatabase() {
    // Abstraktné metódy pre získanie DAO (Data Access Object) pre každú entitu
    abstract fun kategoriaDao(): KategoriaDao
    abstract fun aktivitaDao(): AktivitaDao
    abstract fun ulozeneDao(): UlozeneDao

    // Kompanion objekt pre implementáciu Singleton vzoru pre databázu
    companion object {
        // Volatile anotácia zabezpečuje, že hodnota premennej INSTANCE bude vždy aktuálna pre všetky vlákna
        @Volatile
        private var INSTANCE: Databaza? = null

        // Funkcia pre získanie inštancie databázy
        fun getDatabase(context: Context): Databaza {
            // Ak inštancia nie je nulová, vráti ju, inak vytvorí novú inštanciu synchronizovaným blokom
            return INSTANCE ?: synchronized(this) {
                // Vytvorenie inštancie databázy s fallbackToDestructiveMigration, čo umožňuje deštruktívnu migráciu v prípade zmien v schéme databázy
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Databaza::class.java,
                    "projektlife_database"
                ).fallbackToDestructiveMigration()
                    .build()
                // Priradenie novej inštancie do premennej INSTANCE a jej vrátenie
                INSTANCE = instance
                instance
            }
        }
    }
}

