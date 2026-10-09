package com.example.tugasku.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tugasku.ui.screen.DaftarScreen
import com.example.tugasku.ui.screen.TambahScreen
import com.example.tugasku.ui.viewmodel.TugasViewModel

object Rute {
    const val DAFTAR = "daftar"
    const val TAMBAH = "tambah"
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: TugasViewModel = viewModel() // satu ViewModel dipakai bersama semua layar
) {
    NavHost(navController = navController, startDestination = Rute.DAFTAR) {

        composable(Rute.DAFTAR) {
            val uiState by viewModel.uiState.collectAsState()
            DaftarScreen(
                uiState = uiState,
                onTambahClick = { navController.navigate(Rute.TAMBAH) },
                onTugasClick = { /* Milestone 5: pindah ke layar Detail */ }
            )
        }

        composable(Rute.TAMBAH) {
            TambahScreen(
                onSimpan = { tugas ->
                    viewModel.tambah(tugas)
                    navController.popBackStack()
                },
                onKembali = { navController.popBackStack() }
            )
        }
    }
}