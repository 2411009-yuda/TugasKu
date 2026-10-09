package com.example.tugasku.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.Prioritas
import com.example.tugasku.data.local.StatusTugas
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.ui.component.Pemisah
import com.example.tugasku.ui.component.StatusDot
import com.example.tugasku.ui.state.DaftarUiState
import com.example.tugasku.util.formatTanggal
import com.example.tugasku.util.label

// Stateless: layar hanya menerima state dan mengirim event lewat lambda
@Composable
fun DaftarScreen(
    uiState: DaftarUiState,
    onTambahClick: () -> Unit,
    onTugasClick: (Int) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTambahClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah tugas")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Header(uiState)

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                }

                uiState.tugas.isEmpty() -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Belum ada tugas",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Ketuk tombol + untuk menambah tugas pertamamu.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentPadding = PaddingValues(bottom = 96.dp)
                    ) {
                        items(uiState.tugas, key = { it.id }) { tugas ->
                            TugasRow(tugas = tugas, onClick = { onTugasClick(tugas.id) })
                            Pemisah(modifier = Modifier.padding(horizontal = 24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(uiState: DaftarUiState) {
    val aktif = uiState.tugas.count { it.status != StatusTugas.SELESAI }
    val ringkasan = when {
        uiState.isLoading || uiState.tugas.isEmpty() -> ""
        aktif == 0 -> "Semua tugas selesai"
        else -> "$aktif belum selesai"
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(top = 32.dp, bottom = 16.dp)
    ) {
        Text(text = "Tugas", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = ringkasan,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Satu tugas = satu baris, tanpa kartu dan tanpa bayangan
@Composable
private fun TugasRow(tugas: Tugas, onClick: () -> Unit) {
    val selesai = tugas.status == StatusTugas.SELESAI
    val redup = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        StatusDot(status = tugas.status, modifier = Modifier.padding(top = 6.dp))
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = tugas.judul,
                style = MaterialTheme.typography.titleMedium,
                color = if (selesai) redup else MaterialTheme.colorScheme.onSurface,
                textDecoration = if (selesai) TextDecoration.LineThrough else null,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = tugas.mataKuliah,
                style = MaterialTheme.typography.bodyMedium,
                color = redup,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatTanggal(tugas.deadline),
                style = MaterialTheme.typography.bodySmall,
                color = redup
            )
            Text(
                text = tugas.prioritas.label(),
                style = MaterialTheme.typography.labelMedium,
                color = if (tugas.prioritas == Prioritas.TINGGI && !selesai) {
                    MaterialTheme.colorScheme.error
                } else {
                    redup
                }
            )
        }
    }
}