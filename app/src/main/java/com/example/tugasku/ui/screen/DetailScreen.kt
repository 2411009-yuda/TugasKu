package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.StatusTugas
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.ui.component.AppTopBar
import com.example.tugasku.ui.component.StatusDot
import com.example.tugasku.util.formatTanggal
import com.example.tugasku.util.label

// tugas = null artinya data belum selesai dibaca dari database
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    tugas: Tugas?,
    onEditClick: () -> Unit,
    onStatusChange: (StatusTugas) -> Unit,
    onHapusConfirm: () -> Unit,
    onKembali: () -> Unit
) {
    // State lokal layar ini: dialog sedang tampil atau tidak
    var tampilkanDialogHapus by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(title = "Detail tugas", onKembali = onKembali) }
    ) { padding ->
        if (tugas == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = tugas.judul, style = MaterialTheme.typography.headlineSmall)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusDot(status = tugas.status)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tugas.status.label(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                BarisDetail("Mata kuliah", tugas.mataKuliah)
                BarisDetail("Deadline", formatTanggal(tugas.deadline))
                BarisDetail("Prioritas", tugas.prioritas.label())
                BarisDetail("Deskripsi", tugas.deskripsi.ifBlank { "-" })

                // Pengubah status: perubahan langsung disimpan ke database
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Ubah status",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatusTugas.entries.forEach { pilihan ->
                            FilterChip(
                                selected = tugas.status == pilihan,
                                onClick = { onStatusChange(pilihan) },
                                label = { Text(pilihan.label()) }
                            )
                        }
                    }
                }

                // Satu tombol utama, aksi berisiko dibuat sebagai teks biasa
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth()) {
                        Text("Edit tugas")
                    }
                    TextButton(
                        onClick = { tampilkanDialogHapus = true },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hapus tugas")
                    }
                }
            }

            // Konfirmasi sebelum menghapus
            if (tampilkanDialogHapus) {
                AlertDialog(
                    onDismissRequest = { tampilkanDialogHapus = false },
                    title = { Text("Hapus tugas ini?") },
                    text = {
                        Text("\"${tugas.judul}\" akan dihapus permanen dan tidak bisa dikembalikan.")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                tampilkanDialogHapus = false
                                onHapusConfirm()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text("Hapus") }
                    },
                    dismissButton = {
                        TextButton(onClick = { tampilkanDialogHapus = false }) {
                            Text("Batal")
                        }
                    }
                )
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = nilai, style = MaterialTheme.typography.bodyLarge)
    }
}