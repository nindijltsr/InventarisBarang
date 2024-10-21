package com.example.inventarisbarang.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "karyawan")
data class Karyawan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaKaryawan: String,
    val jabatan: String,
    val kontak: String
)
