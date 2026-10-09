package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.util.formatTanggal
import com.example.tugasku.util.label

// tugas = null artinya data belum selesai dibaca dari database
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    tugas: Tugas?,
    onEditClick: () -> Unit,
    onKembali: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = tugas.judul, style = MaterialTheme.typography.headlineSmall)
                BarisDetail("Mata kuliah", tugas.mataKuliah)
                BarisDetail("Deadline", formatTanggal(tugas.deadline))
                BarisDetail("Prioritas", tugas.prioritas.label())
                BarisDetail("Status", tugas.status.label())
                BarisDetail("Deskripsi", tugas.deskripsi.ifBlank { "-" })

                Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Edit")
                }
            }
        }
    }
}

@Composable
private fun BarisDetail(label: String, nilai: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = nilai, style = MaterialTheme.typography.bodyLarge)
    }
}