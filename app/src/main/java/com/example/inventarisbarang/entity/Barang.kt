package com.example.inventarisbarang.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val nama: String,
    val kategori: String,
    val jumlah: Int,
    val tanggalMasuk: String,
    val kondisi: String,
    val ruanganId: Long,
    val karyawanId: Long
)
