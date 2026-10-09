package com.example.tugasku.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasku.data.local.AppDatabase
import com.example.tugasku.data.local.StatusTugas
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.data.repository.TugasRepository
import com.example.tugasku.ui.state.DaftarUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TugasViewModel(application: Application) : AndroidViewModel(application) {

    // ViewModel hanya mengenal Repository, bukan Room secara langsung di UI
    private val repository = TugasRepository(
        AppDatabase.getDatabase(application).tugasDao()
    )

    // UI state untuk layar Daftar. Setiap data di database berubah,
    // nilai ini ikut berubah dan Compose menggambar ulang daftarnya.
    val uiState: StateFlow<DaftarUiState> = repository.semuaTugas
        .map { daftar -> DaftarUiState(tugas = daftar, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DaftarUiState()
        )

    // Untuk layar Detail dan Edit: baca satu tugas berdasarkan id
    fun getTugas(id: Int): Flow<Tugas?> = repository.getTugas(id)

    // Event dari UI
    fun tambah(tugas: Tugas) {
        viewModelScope.launch { repository.tambah(tugas) }
    }

    fun ubah(tugas: Tugas) {
        viewModelScope.launch { repository.ubah(tugas) }
    }

    fun hapus(tugas: Tugas) {
        viewModelScope.launch { repository.hapus(tugas) }
    }

    fun ubahStatus(tugas: Tugas, statusBaru: StatusTugas) {
        ubah(tugas.copy(status = statusBaru))
    }
}