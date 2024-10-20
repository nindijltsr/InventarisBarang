package com.example.inventarisbarang

import androidx.lifecycle.LiveData
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

class InventarisRepository(
    private val barangDao: BarangDao,
    private val ruanganDao: RuanganDao,
    private val karyawanDao: KaryawanDao
) {
    val allBarang: LiveData<List<Barang>> = barangDao.getAllBarang()
    val allRuangan: LiveData<List<Ruangan>> = ruanganDao.getAllRuangan()
    val allKaryawan: LiveData<List<Karyawan>> = karyawanDao.getAllKaryawan()

    suspend fun insert(barang: Barang) {
        barangDao.insert(barang)
    }

    suspend fun update(barang: Barang) {
        barangDao.update(barang)
    }

    suspend fun delete(barang: Barang) {
        barangDao.delete(barang)
    }

    suspend fun insert(ruangan: Ruangan) {
        ruanganDao.insert(ruangan)
    }

    suspend fun update(ruangan: Ruangan) {
        ruanganDao.update(ruangan)
    }

    suspend fun delete(ruangan: Ruangan) {
        ruanganDao.delete(ruangan)
    }

    suspend fun insert(karyawan: Karyawan) {
        karyawanDao.insert(karyawan)
    }

    suspend fun update(karyawan: Karyawan) {
        karyawanDao.update(karyawan)
    }

    suspend fun delete(karyawan: Karyawan) {
        karyawanDao.delete(karyawan)
    }
}
