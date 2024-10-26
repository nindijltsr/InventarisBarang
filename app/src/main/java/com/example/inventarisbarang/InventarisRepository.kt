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

//    barang
    suspend fun insert(barang: Barang) {
        barangDao.insert(barang)
    }

    suspend fun update(barang: Barang) {
        barangDao.update(barang)
    }

    suspend fun delete(barang: Barang) {
        barangDao.delete(barang)
    }

    fun getBarangById(barangId: Long): LiveData<Barang> {
        return barangDao.getBarangById(barangId)
    }
//Ruangan
    suspend fun insert(ruangan: Ruangan) {
        ruanganDao.insert(ruangan)
    }

    fun getRuanganById(ruanganId: Long): LiveData<Ruangan> {
        return ruanganDao.getRuanganById(ruanganId)
    }
//Karyawan
    suspend fun insert(karyawan: Karyawan) {
        karyawanDao.insert(karyawan)
    }

    fun getKaryawanById(karyawanId: Long): LiveData<Karyawan> {
        return karyawanDao.getKaryawanById(karyawanId)
    }
}
