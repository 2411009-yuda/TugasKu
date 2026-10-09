package com.example.tugasku.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasku.ui.screen.DaftarScreen
import com.example.tugasku.ui.screen.DetailScreen
import com.example.tugasku.ui.screen.EditScreen
import com.example.tugasku.ui.screen.TambahScreen
import com.example.tugasku.ui.viewmodel.TugasViewModel

object Rute {
    const val ARG_ID = "id"

    const val DAFTAR = "daftar"
    const val TAMBAH = "tambah"
    const val DETAIL = "detail/{$ARG_ID}"
    const val EDIT = "edit/{$ARG_ID}"

    fun detail(id: Int) = "detail/$id"
    fun edit(id: Int) = "edit/$id"
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: TugasViewModel = viewModel() // satu ViewModel dipakai bersama semua layar
) {
    val argId = listOf(navArgument(Rute.ARG_ID) { type = NavType.IntType })

    NavHost(navController = navController, startDestination = Rute.DAFTAR) {

        composable(Rute.DAFTAR) {
            val uiState by viewModel.uiState.collectAsState()
            DaftarScreen(
                uiState = uiState,
                onTambahClick = { navController.navigate(Rute.TAMBAH) },
                onTugasClick = { id -> navController.navigate(Rute.detail(id)) }
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

        composable(Rute.DETAIL, arguments = argId) { entry ->
            val id = entry.arguments?.getInt(Rute.ARG_ID) ?: 0
            val tugas by remember(id) { viewModel.getTugas(id) }.collectAsState(initial = null)
            DetailScreen(
                tugas = tugas,
                onEditClick = { navController.navigate(Rute.edit(id)) },
                onStatusChange = { statusBaru ->
                    tugas?.let { viewModel.ubahStatus(it, statusBaru) }
                },
                onHapusConfirm = {
                    tugas?.let { viewModel.hapus(it) }
                    navController.popBackStack()
                },
                onKembali = { navController.popBackStack() }
            )
        }

        composable(Rute.EDIT, arguments = argId) { entry ->
            val id = entry.arguments?.getInt(Rute.ARG_ID) ?: 0
            val tugas by remember(id) { viewModel.getTugas(id) }.collectAsState(initial = null)
            EditScreen(
                tugas = tugas,
                onSimpan = { tugasBaru ->
                    viewModel.ubah(tugasBaru)
                    // Kembali ke Daftar, melewati layar Detail
                    navController.popBackStack(Rute.DAFTAR, inclusive = false)
                },
                onKembali = { navController.popBackStack() }
            )
        }
    }
}