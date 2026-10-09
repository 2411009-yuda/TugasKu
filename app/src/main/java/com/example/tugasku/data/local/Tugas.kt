package com.example.tugasku.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class StatusTugas { BELUM, PROSES, SELESAI }

enum class Prioritas { RENDAH, SEDANG, TINGGI }

@Entity(tableName = "tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: Long, // milidetik sejak epoch, agar mudah diurutkan
    val prioritas: Prioritas = Prioritas.SEDANG,
    val status: StatusTugas = StatusTugas.BELUM
)