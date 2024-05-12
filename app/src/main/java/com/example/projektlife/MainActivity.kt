package com.example.projektlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projektlife.dataclass.Akcia
import com.example.projektlife.dataclass.Kategoria
import com.example.projektlife.ui.theme.ProjektLifeTheme
import com.example.projektlife.obrazovky.ui.MainScreen

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CreateCategory : Screen("create_category")
    object CreateAction : Screen("create_action")
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
    }
}
