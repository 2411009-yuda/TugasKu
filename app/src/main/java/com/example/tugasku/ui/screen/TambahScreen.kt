package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.ui.component.AppTopBar

@Composable
fun TambahScreen(
    onSimpan: (Tugas) -> Unit,
    onKembali: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(title = "Tambah tugas", onKembali = onKembali) }
    ) { padding ->
        TugasForm(
            tugasAwal = null,
            labelTombol = "Simpan",
            onSimpan = onSimpan,
            modifier = Modifier.padding(padding)
        )
    }
}