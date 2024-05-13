package com.example.projektlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projektlife.obrazovky.ui.AktivitaScreen
import com.example.projektlife.obrazovky.ui.KategoriaScreen
import com.example.projektlife.ui.theme.ProjektLifeTheme
import com.example.projektlife.obrazovky.ui.MainScreen

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CreateCategory : Screen("add_kategoria")
    object CreateAction : Screen("add_aktivita")
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjektLifeTheme {
                AppNavigator();
                }
            }
        }
    }
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) { MainScreen(navController) }
        composable(Screen.CreateCategory.route) { KategoriaScreen(navController) }
        composable(Screen.CreateAction.route) { AktivitaScreen(navController) }
    }
}
