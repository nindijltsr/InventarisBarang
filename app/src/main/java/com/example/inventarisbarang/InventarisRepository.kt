package com.example.inventarisbarang

import androidx.lifecycle.LiveData
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import com.google.firebase.database.FirebaseDatabase

class InventarisRepository(
    private val barangDao: BarangDao,
    private val ruanganDao: RuanganDao,
    private val karyawanDao: KaryawanDao
) {
    private val database = FirebaseDatabase.getInstance()
    private val barangRef = database.getReference("barang")
    private val karyawanRef = database.getReference("karyawan")
    private val ruanganRef = database.getReference("ruangan")

    val allBarang: LiveData<List<Barang>> = barangDao.getAllBarang()
    val allRuangan: LiveData<List<Ruangan>> = ruanganDao.getAllRuangan()
    val allKaryawan: LiveData<List<Karyawan>> = karyawanDao.getAllKaryawan()

    // Barang CRUD
    suspend fun insert(barang: Barang) {
        val newId = barang.id.toString() // Gunakan ID dari Room
        barangDao.insert(barang) // Simpan ke Room
        barangRef.child(newId).setValue(barang) // Gunakan ID yang sama untuk Firebase
    }

    suspend fun update(barang: Barang) {
        val key = barang.id.toString()
        barangDao.update(barang) // Update di Room
        barangRef.child(key).setValue(barang) // Update di Firebase menggunakan key yang sama
    }

    suspend fun delete(barang: Barang) {
        barangDao.delete(barang) // Hapus dari Room
        barangRef.child(barang.id.toString()).removeValue() // Hapus dari Firebase
    }

    fun getBarangById(barangId: Long): LiveData<Barang> {
        return barangDao.getBarangById(barangId.toString()) // Room expects Long, so no conversion here
    }


    // Ruangan CRUD
    suspend fun insert(ruangan: Ruangan) {
        val newId = ruangan.id.toString() // Gunakan ID dari Room
        ruanganDao.insert(ruangan) // Simpan ke Room
        ruanganRef.child(newId).setValue(ruangan) // Gunakan ID yang sama untuk Firebase
    }

    suspend fun update(ruangan: Ruangan) {
        // Gunakan ruangan.id sebagai key
        val key = ruangan.id.toString()
        ruanganDao.update(ruangan) // Update di Room
        ruanganRef.child(key).setValue(ruangan) // Update di Firebase menggunakan key yang sama
    }

    suspend fun delete(ruangan: Ruangan) {
        ruanganDao.delete(ruangan) // Hapus dari Room
        ruanganRef.child(ruangan.id.toString()).removeValue() // Hapus dari Firebase
    }

    fun getRuanganById(ruanganId: Long): LiveData<Ruangan> {
        return ruanganDao.getRuanganById(ruanganId.toString())
    }

    // Karyawan CRUD
    suspend fun insert(karyawan: Karyawan) {
        val newId = karyawan.id.toString() // Gunakan ID dari Room
        karyawanDao.insert(karyawan) // Simpan ke Room
        karyawanRef.child(newId).setValue(karyawan) // Gunakan ID yang sama untuk Firebase
    }

    suspend fun update(karyawan: Karyawan) {
        // Gunakan karyawan.id sebagai key
        val key = karyawan.id.toString()
        karyawanDao.update(karyawan) // Update di Room
        karyawanRef.child(key)
            .setValue(karyawan) // Update di Firebase menggunakan key yang sama
    }

    suspend fun delete(karyawan: Karyawan) {
        karyawanDao.delete(karyawan) // Hapus dari Room
        karyawanRef.child(karyawan.id.toString()).removeValue() // Hapus dari Firebase
    }

    fun getKaryawanById(karyawanId: Long): LiveData<Karyawan> {
        return karyawanDao.getKaryawanById(karyawanId.toString())
    }
    }