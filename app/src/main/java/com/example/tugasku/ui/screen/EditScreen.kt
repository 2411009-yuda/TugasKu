package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tugasku.data.local.Tugas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    tugas: Tugas?,
    onSimpan: (Tugas) -> Unit,
    onKembali: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Tugas") },
                navigationIcon = {
                    IconButton(onClick = onKembali) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (tugas == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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