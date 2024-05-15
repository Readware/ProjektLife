package com.example.projektlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projektlife.databaza.Databaza
import com.example.projektlife.obrazovky.ui.AktivitaScreen
import com.example.projektlife.obrazovky.ui.KategoriaEditScreen
import com.example.projektlife.obrazovky.ui.KategoriaScreen
import com.example.projektlife.obrazovky.ui.KategorieUpravaScreen
import com.example.projektlife.obrazovky.ui.MainScreen
import com.example.projektlife.obrazovky.ui.NastaveniaScreen
import com.example.projektlife.obrazovky.ui.StatisticsScreen
import com.example.projektlife.repository.AktivitaRepository
import com.example.projektlife.repository.DatabaseFactory
import com.example.projektlife.repository.KategorieRepository
import com.example.projektlife.repository.UlozeneRepository
import com.example.projektlife.ui.theme.ProjektLifeTheme
import com.example.projektlife.viewmodel.AktivitaView
import com.example.projektlife.viewmodel.KategoriaView
import com.example.projektlife.viewmodel.UlozeneView

// Definícia triedy Screen, ktorá reprezentuje rôzne obrazovky v aplikácii
sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CreateCategory : Screen("add_kategoria")
    object CreateAction : Screen("add_aktivita")
    object UpravitKategoriu : Screen("kategorie_uprava")
    object Statistika : Screen("statistics")
    object Nastavenia : Screen("nastavenia")
}

// Hlavná aktivita aplikácie
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Povolenie edge-to-edge režimu pre celú obrazovku
        setContent {
            ProjektLifeTheme { // Nastavenie témy aplikácie
                AppNavigator() // Spustenie composable funkcie pre navigáciu
            }
        }
    }
}

// Composable funkcia pre navigáciu v aplikácii
@Composable
fun AppNavigator() {
    val navController = rememberNavController() // Vytvorenie navigačného kontrolóra
    val context = LocalContext.current.applicationContext
    val db = remember { Databaza.getDatabase(context) } // Získanie databázy

    // Vytvorenie repository a view modelov
    val kategoriaRepository = remember { KategorieRepository(db.kategoriaDao()) }
    val aktivitaRepository = remember { AktivitaRepository(db.aktivitaDao(), db.kategoriaDao()) }
    val ulozeneRepository = remember { UlozeneRepository(db.ulozeneDao()) }
    val viewModelFactory = remember { DatabaseFactory(kategoriaRepository, aktivitaRepository, ulozeneRepository) }

    val kategoriaViewModel: KategoriaView = viewModel(factory = viewModelFactory)
    val aktivitaViewModel: AktivitaView = viewModel(factory = viewModelFactory)
    val ulozeneViewModel: UlozeneView = viewModel(factory = viewModelFactory)

    // Definícia navigačného hostiteľa
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController, aktivitaViewModel, ulozeneViewModel)
        }
        composable(Screen.CreateCategory.route) {
            KategoriaScreen(navController, kategoriaViewModel)
        }
        composable(Screen.UpravitKategoriu.route) {
            KategorieUpravaScreen(navController = navController, kategoriaViewModel)
        }
        composable(Screen.Nastavenia.route) {
            NastaveniaScreen(navController = navController, kategoriaViewModel, aktivitaViewModel, ulozeneViewModel)
        }
        // Definícia obrazovky pre editáciu kategórie s argumentom kategoriaId
        composable(
            route = "kategoria_edit/{kategoriaId}",
            arguments = listOf(navArgument("kategoriaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val kategoriaId = backStackEntry.arguments?.getInt("kategoriaId") ?: return@composable
            KategoriaEditScreen(navController = navController, kategoriaId = kategoriaId, kategoriaViewModel)
        }
        composable(Screen.Statistika.route) {
            StatisticsScreen(navController = navController, ulozeneViewModel)
        }
        composable(Screen.CreateAction.route) {
            AktivitaScreen(navController, kategoriaViewModel, aktivitaViewModel)
        }
    }
}
