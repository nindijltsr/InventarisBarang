package com.example.inventarisbarang.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan

@Dao
interface KaryawanDao {
    @Insert
    suspend fun insert(karyawan: Karyawan)

    @Update
    suspend fun update(karyawan: Karyawan)

    @Delete
    suspend fun delete(karyawan: Karyawan)

    @Query("SELECT * FROM karyawan")
    fun getAllKaryawan(): LiveData<List<Karyawan>>

    @Query("SELECT * FROM karyawan WHERE id = :karyawanid")
    fun getKaryawanById(karyawanid: Long): LiveData<Karyawan>

    @Query("SELECT * FROM karyawan")
    fun getAll(): Array<Karyawan>
}
