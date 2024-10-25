package com.example.inventarisbarang.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.inventarisbarang.entity.Karyawan

@Dao
interface KaryawanDao {
    @Insert
    suspend fun insert(karyawan: Karyawan)

    @Query("SELECT * FROM karyawan")
    fun getAllKaryawan(): LiveData<List<Karyawan>>

    @Query("SELECT * FROM karyawan WHERE id = :karyawanid")
    fun getKaryawanById(karyawanid: Long): LiveData<Karyawan>
}
