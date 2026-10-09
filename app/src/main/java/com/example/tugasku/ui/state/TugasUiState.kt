package com.example.tugasku.ui.state

import com.example.tugasku.data.local.Tugas

// Bentuk data yang dibaca layar Daftar. Layar tidak tahu soal Room.
data class DaftarUiState(
    val tugas: List<Tugas> = emptyList(),
    val isLoading: Boolean = true
)