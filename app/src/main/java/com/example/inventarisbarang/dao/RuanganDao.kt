package com.example.inventarisbarang.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventarisbarang.entity.Ruangan

@Dao
interface RuanganDao {
    @Insert
    suspend fun insert(ruangan: Ruangan)

    @Update
    suspend fun update(ruangan: Ruangan)

    @Delete
    suspend fun delete(ruangan: Ruangan)

    @Query("SELECT * FROM ruangan")
    fun getAllRuangan(): LiveData<List<Ruangan>>

    @Query("SELECT * FROM ruangan WHERE id = :ruanganId")
    fun getRuanganById(ruanganId: Int): LiveData<Ruangan>
}
