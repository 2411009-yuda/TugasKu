package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.ui.state.DaftarUiState
import com.example.tugasku.util.formatTanggal
import com.example.tugasku.util.label

// Stateless: layar hanya menerima state dan mengirim event lewat lambda
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarScreen(
    uiState: DaftarUiState,
    onTambahClick: () -> Unit,
    onTugasClick: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Tugas Saya") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah tugas")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.tugas.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada tugas.\nKetuk tombol + untuk menambah.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.tugas, key = { it.id }) { tugas ->
                        TugasItem(tugas = tugas, onClick = { onTugasClick(tugas.id) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TugasItem(tugas: Tugas, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = tugas.judul, style = MaterialTheme.typography.titleMedium)
            Text(text = tugas.mataKuliah, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Deadline: ${formatTanggal(tugas.deadline)}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Prioritas: ${tugas.prioritas.label()}  •  Status: ${tugas.status.label()}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}