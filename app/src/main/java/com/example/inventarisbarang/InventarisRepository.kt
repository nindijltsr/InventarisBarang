package com.example.inventarisbarang

import androidx.lifecycle.LiveData
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.entity.Barang

class InventarisRepository(private val barangDao: BarangDao) {

    val allBarang: LiveData<List<Barang>> = barangDao.getAllBarang()

    suspend fun insert(barang: Barang) {
        barangDao.insert(barang)
    }

    suspend fun update(barang: Barang) {
        barangDao.update(barang)
    }

    suspend fun delete(barang: Barang) {
        barangDao.delete(barang)
    }

}
