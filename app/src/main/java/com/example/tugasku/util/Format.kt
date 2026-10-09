package com.example.tugasku.util

import com.example.tugasku.data.local.Prioritas
import com.example.tugasku.data.local.StatusTugas
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// DatePicker Material 3 menghasilkan tengah malam UTC, jadi diformat dengan UTC
// agar tanggal yang tampil sama dengan tanggal yang dipilih.
fun formatTanggal(millis: Long): String {
    val format = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(Date(millis))
}

fun Prioritas.label(): String = when (this) {
    Prioritas.RENDAH -> "Rendah"
    Prioritas.SEDANG -> "Sedang"
    Prioritas.TINGGI -> "Tinggi"
}

fun StatusTugas.label(): String = when (this) {
    StatusTugas.BELUM -> "Belum"
    StatusTugas.PROSES -> "Proses"
    StatusTugas.SELESAI -> "Selesai"
}