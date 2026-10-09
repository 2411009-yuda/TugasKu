package com.example.tugasku.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tugasku.data.local.StatusTugas
import com.example.tugasku.ui.theme.StatusBelum
import com.example.tugasku.ui.theme.StatusProses
import com.example.tugasku.ui.theme.StatusSelesai

// App bar datar: warnanya sama dengan latar, tanpa bayangan
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String, onKembali: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(onClick = onKembali) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

// Garis pemisah tipis (dibuat manual agar tidak bergantung pada versi Material3)
@Composable
fun Pemisah(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}

// Titik kecil penanda status tugas
@Composable
fun StatusDot(status: StatusTugas, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(status.warna())
    )
}

fun StatusTugas.warna(): Color = when (this) {
    StatusTugas.BELUM -> StatusBelum
    StatusTugas.PROSES -> StatusProses
    StatusTugas.SELESAI -> StatusSelesai
}