package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.ui.component.AppTopBar

@Composable
fun EditScreen(
    tugas: Tugas?,
    onSimpan: (Tugas) -> Unit,
    onKembali: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(title = "Edit tugas", onKembali = onKembali) }
    ) { padding ->
        if (tugas == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        } else {
            // Form baru dibuat setelah data lama tersedia, supaya isian awalnya terisi
            TugasForm(
                tugasAwal = tugas,
                labelTombol = "Simpan perubahan",
                onSimpan = onSimpan,
                modifier = Modifier.padding(padding)
            )
        }
    }
}