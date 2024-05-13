import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
data class Kategoria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val typ: Typ,
    val nazov: String,
    @TypeConverters(ColorConverter::class) val farba: Color
)

enum class Typ {
    POSITIVNA, NEGATIVNA, NEUTRALNA
}

class ColorConverter {
    @TypeConverter
    fun fromColor(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(value: Long): Color = Color(value)
}
