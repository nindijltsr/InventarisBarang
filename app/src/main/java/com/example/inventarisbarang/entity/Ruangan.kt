package com.example.inventarisbarang.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruangan")
data class Ruangan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaRuangan: String
)
