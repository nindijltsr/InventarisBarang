package com.example.inventarisbarang.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.inventarisbarang.entity.Ruangan

@Dao
interface RuanganDao {
    @Insert
    suspend fun insert(ruangan: Ruangan)

    @Query("SELECT * FROM ruangan")
    fun getAllRuangan(): LiveData<List<Ruangan>>

    @Query("SELECT * FROM ruangan WHERE id = :ruanganId")
    fun getRuanganById(ruanganId: Long): LiveData<Ruangan>
}
