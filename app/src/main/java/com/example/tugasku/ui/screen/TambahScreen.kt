package com.example.tugasku.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.tugasku.data.local.Tugas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahScreen(
    onSimpan: (Tugas) -> Unit,
    onKembali: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Tugas") },
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
        TugasForm(
            tugasAwal = null,
            labelTombol = "Simpan",
            onSimpan = onSimpan,
            modifier = Modifier.padding(padding)
        )
    }
}