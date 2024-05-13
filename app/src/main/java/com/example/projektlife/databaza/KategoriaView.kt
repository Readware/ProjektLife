import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.projektlife.databaza.Databaza

class KategoriaView(context: Context) : ViewModel() {
    private val db = Databaza.getInstance(context)
    private val _kategorie = MutableStateFlow<List<Kategoria>>(emptyList())
    val kategorie: StateFlow<List<Kategoria>> = _kategorie

    init {
        fetchKategorie()
    }

    private fun fetchKategorie() {
        viewModelScope.launch(Dispatchers.IO) {
            _kategorie.value = db.kategoriaDao().getAll()
        }
    }
}
