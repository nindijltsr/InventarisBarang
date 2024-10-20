package com.example.inventarisbarang.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "karyawan")
data class Karyawan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val posisi: String,
    val kontak: String
)
