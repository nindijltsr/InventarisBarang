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
        barangDao.insert(barang) // Save to Room
        val key = barangRef.push().key ?: return
        barangRef.child(key).setValue(barang) // Save to Firebase
    }

    suspend fun update(barang: Barang) {
        barangDao.update(barang) // Update Room
        barangRef.child(barang.id.toString()).setValue(barang) // Update Firebase
    }

    suspend fun delete(barang: Barang) {
        barangDao.delete(barang) // Delete from Room
        barangRef.child(barang.id.toString()).removeValue() // Delete from Firebase
    }

    fun getBarangById(barangId: Long): LiveData<Barang> {
        return barangDao.getBarangById(barangId)
    }

    // Ruangan CRUD
    suspend fun insert(ruangan: Ruangan) {
        ruanganDao.insert(ruangan) // Save to Room
        val key = ruanganRef.push().key ?: return
        ruanganRef.child(key).setValue(ruangan) // Save to Firebase
    }

    suspend fun update(ruangan: Ruangan) {
        ruanganDao.update(ruangan) // Update Room
        ruanganRef.child(ruangan.id.toString()).setValue(ruangan) // Update Firebase
    }

    suspend fun delete(ruangan: Ruangan) {
        ruanganDao.delete(ruangan) // Delete from Room
        ruanganRef.child(ruangan.id.toString()).removeValue() // Delete from Firebase
    }

    fun getRuanganById(ruanganId: Long): LiveData<Ruangan> {
        return ruanganDao.getRuanganById(ruanganId)
    }

    // Karyawan CRUD
    suspend fun insert(karyawan: Karyawan) {
        karyawanDao.insert(karyawan) // Save to Room
        val key = karyawanRef.push().key ?: return
        karyawanRef.child(key).setValue(karyawan) // Save to Firebase
    }

    suspend fun update(karyawan: Karyawan) {
        karyawanDao.update(karyawan) // Update Room
        karyawanRef.child(karyawan.id.toString()).setValue(karyawan) // Update Firebase
    }

    suspend fun delete(karyawan: Karyawan) {
        karyawanDao.delete(karyawan) // Delete from Room
        karyawanRef.child(karyawan.id.toString()).removeValue() // Delete from Firebase
    }

    fun getKaryawanById(karyawanId: Long): LiveData<Karyawan> {
        return karyawanDao.getKaryawanById(karyawanId)
    }
}