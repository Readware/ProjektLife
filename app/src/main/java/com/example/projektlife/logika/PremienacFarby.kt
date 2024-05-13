package com.example.projektlife.logika

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
class PremienacFarby {
    @TypeConverter
    fun fromColor(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(value: Long): Color = Color(value)
}