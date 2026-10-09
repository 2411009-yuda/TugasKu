package com.example.tugasku.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Query("SELECT * FROM tugas ORDER BY deadline ASC")
    fun getSemuaTugas(): Flow<List<Tugas>>

    @Query("SELECT * FROM tugas WHERE id = :id")
    fun getTugasById(id: Int): Flow<Tugas?>

    @Insert
    suspend fun insert(tugas: Tugas)

    @Update
    suspend fun update(tugas: Tugas)

    @Delete
    suspend fun delete(tugas: Tugas)
}