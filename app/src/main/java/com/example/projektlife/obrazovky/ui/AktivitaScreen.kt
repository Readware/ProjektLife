package com.example.projektlife.obrazovky.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.projektlife.viewmodel.KategoriaView

import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AktivitaScreen(navController: NavHostController, KategoriaView: KategoriaView = viewModel()) {
//    val kategorie = KategoriaView.kategorie.collectAsState().value
//
//    Column {
//        Text("Vyberte kategóriu")
//        LazyColumn {
//            items(kategorie) { kategoria ->
//                Text(
//                    kategoria.nazov,
//                    modifier = Modifier.clickable {
//
//                    }
//                )
//            }
//        }
//    }
}
