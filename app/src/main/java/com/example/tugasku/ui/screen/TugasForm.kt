package com.example.tugasku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.Prioritas
import com.example.tugasku.data.local.StatusTugas
import com.example.tugasku.data.local.Tugas
import com.example.tugasku.util.formatTanggal
import com.example.tugasku.util.label

// Form dipakai untuk Tambah (tugasAwal = null) dan Edit (tugasAwal = data lama).
// State isian disimpan di sini dengan rememberSaveable agar tidak hilang saat layar diputar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TugasForm(
    tugasAwal: Tugas?,
    labelTombol: String,
    onSimpan: (Tugas) -> Unit,
    modifier: Modifier = Modifier
) {
    var mataKuliah by rememberSaveable { mutableStateOf(tugasAwal?.mataKuliah ?: "") }
    var judul by rememberSaveable { mutableStateOf(tugasAwal?.judul ?: "") }
    var deskripsi by rememberSaveable { mutableStateOf(tugasAwal?.deskripsi ?: "") }
    var deadline by rememberSaveable { mutableStateOf(tugasAwal?.deadline) }
    var prioritas by rememberSaveable { mutableStateOf(tugasAwal?.prioritas ?: Prioritas.SEDANG) }
    var status by rememberSaveable { mutableStateOf(tugasAwal?.status ?: StatusTugas.BELUM) }

    var mataKuliahTersentuh by rememberSaveable { mutableStateOf(false) }
    var judulTersentuh by rememberSaveable { mutableStateOf(false) }
    var tampilkanDatePicker by rememberSaveable { mutableStateOf(false) }

    // Validasi: pesan error muncul setelah field disentuh lalu dikosongkan
    val mataKuliahError = mataKuliahTersentuh && mataKuliah.isBlank()
    val judulError = judulTersentuh && judul.isBlank()
    val formValid = mataKuliah.isNotBlank() && judul.isNotBlank() && deadline != null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = mataKuliah,
            onValueChange = {
                mataKuliah = it
                mataKuliahTersentuh = true
            },
            label = { Text("Mata kuliah") },
            isError = mataKuliahError,
            supportingText = { if (mataKuliahError) Text("Mata kuliah wajib diisi") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = judul,
            onValueChange = {
                judul = it
                judulTersentuh = true
            },
            label = { Text("Judul tugas") },
            isError = judulError,
            supportingText = { if (judulError) Text("Judul wajib diisi") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = { Text("Deskripsi (opsional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = { tampilkanDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = deadline?.let { "Deadline: ${formatTanggal(it)}" }
                    ?: "Pilih deadline (wajib)"
            )
        }

        Text(text = "Prioritas", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Prioritas.entries.forEach { pilihan ->
                FilterChip(
                    selected = prioritas == pilihan,
                    onClick = { prioritas = pilihan },
                    label = { Text(pilihan.label()) }
                )
            }
        }

        // Status hanya muncul saat mengedit; tugas baru selalu dimulai dari "Belum"
        if (tugasAwal != null) {
            Text(text = "Status", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusTugas.entries.forEach { pilihan ->
                    FilterChip(
                        selected = status == pilihan,
                        onClick = { status = pilihan },
                        label = { Text(pilihan.label()) }
                    )
                }
            }
        }

        // Tombol aktif hanya saat data valid
        Button(
            onClick = {
                onSimpan(
                    Tugas(
                        id = tugasAwal?.id ?: 0,
                        mataKuliah = mataKuliah.trim(),
                        judul = judul.trim(),
                        deskripsi = deskripsi.trim(),
                        deadline = deadline!!, // aman: tombol hanya aktif jika deadline != null
                        prioritas = prioritas,
                        status = status
                    )
                )
            },
            enabled = formValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(labelTombol)
        }
    }

    if (tampilkanDatePicker) {
        val pickerState = rememberDatePickerState(initialSelectedDateMillis = deadline)
        DatePickerDialog(
            onDismissRequest = { tampilkanDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        deadline = pickerState.selectedDateMillis
                        tampilkanDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { tampilkanDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }
}