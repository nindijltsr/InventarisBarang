package com.example.inventarisbarang.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventarisbarang.entity.Barang

@Dao
interface BarangDao {
    @Insert
    suspend fun insert(barang: Barang)

    @Update
    suspend fun update(barang: Barang)

    @Delete
    suspend fun delete(barang: Barang)

    @Query("SELECT * FROM barang")
    fun getAllBarang(): LiveData<List<Barang>>

    @Query("SELECT * FROM barang WHERE id = :barangId")
    fun getBarangById(barangId: Long): LiveData<Barang>

    @Query("SELECT * FROM barang")
    fun getAll(): Array<Barang>
}