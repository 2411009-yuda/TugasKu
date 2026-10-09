package com.example.tugasku.data.repository

import com.example.tugasku.data.local.Tugas
import com.example.tugasku.data.local.TugasDao
import kotlinx.coroutines.flow.Flow

// ViewModel hanya berbicara dengan Repository, tidak tahu detail Room
class TugasRepository(private val tugasDao: TugasDao) {

    val semuaTugas: Flow<List<Tugas>> = tugasDao.getSemuaTugas()

    fun getTugas(id: Int): Flow<Tugas?> = tugasDao.getTugasById(id)

    suspend fun tambah(tugas: Tugas) = tugasDao.insert(tugas)

    suspend fun ubah(tugas: Tugas) = tugasDao.update(tugas)

    suspend fun hapus(tugas: Tugas) = tugasDao.delete(tugas)
}