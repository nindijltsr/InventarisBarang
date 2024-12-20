package com.example.inventarisbarang

import androidx.lifecycle.LiveData
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.google.firebase.database.DatabaseReference

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
        try {
            val barangList = barangRef.get().await().children
            val newId = (barangList.count() + 1).toLong()
            barang.id = newId
            barangDao.insert(barang)
            barangRef.child(barang.nama).setValue(barang)
            Log.d("Repository", "Barang berhasil ditambahkan ke Firebase: $barang")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan barang: ${e.message}")
        }
    }

    suspend fun update(barang: Barang, oldNama: String) {
        try {
            barangDao.update(barang)
            if (barang.nama != oldNama) {
                barangRef.child(oldNama).removeValue()
            }

            barangRef.child(barang.nama).setValue(barang)
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui barang: ${e.message}")
        }
    }

    suspend fun delete(barang: Barang) {
        try {
            barangDao.delete(barang)
            barangRef.child(barang.nama).removeValue().await()
            Log.d("Repository", "Barang berhasil dihapus dari Firebase: $barang")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menghapus barang: ${e.message}")
        }
    }

    fun getBarangById(barangId: Long): LiveData<Barang> {
        return barangDao.getBarangById(barangId.toString())
    }

    //Ruangan
    suspend fun insert(ruangan: Ruangan) {
        try {
            val existingRuangan = ruanganRef.child(ruangan.namaRuangan).get().await()
            if (existingRuangan.exists()) {
                Log.e("Repository", "Ruangan dengan nama ${ruangan.namaRuangan} sudah ada di Firebase")
                return
            }

            val ruanganList = ruanganRef.get().await().children
            val newId = (ruanganList.count() + 1).toLong() // Set ID locally (for Room)

            ruangan.id = newId

            ruanganDao.insert(ruangan)
            ruanganRef.child(ruangan.namaRuangan).setValue(ruangan).await()
            Log.d("Repository", "Ruangan berhasil ditambahkan ke Firebase: $ruangan")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan ruangan: ${e.message}")

        }
    }

    suspend fun update(ruangan: Ruangan, oldNama: String) {
        try {
            ruanganDao.update(ruangan)
            if (ruangan.namaRuangan != oldNama) {
                ruanganRef.child(oldNama).removeValue()
            }
            ruanganRef.child(ruangan.namaRuangan).setValue(ruangan)
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui karyawan: ${e.message}")
        }
    }

    suspend fun delete(ruangan: Ruangan) {
        try {
            ruanganDao.delete(ruangan)
            ruanganRef.child(ruangan.namaRuangan).removeValue().await()
            Log.d("Repository", "Ruangan berhasil dihapus dari Firebase: $ruangan")
        }
        catch (e: Exception) {
            Log.e("Repository", "Gagal menghapus ruangan: ${e.message}")

        }
    }

    fun getRuanganById(ruanganId: Long): LiveData<Ruangan> {
        return ruanganDao.getRuanganById(ruanganId.toString())
    }

    //Karyawan
    suspend fun insert(karyawan: Karyawan) {
        try {
            val existingKaryawan = karyawanRef.child(karyawan.namaKaryawan).get().await()
            if (existingKaryawan.exists()) {
                Log.e("Repository", "Karyawan dengan nama ${karyawan.namaKaryawan} sudah ada di Firebase")
                return
            }

            val karyawanList = karyawanRef.get().await().children
            val newId = (karyawanList.count() + 1).toLong() // Set ID locally (for Room)

            karyawan.id = newId

            karyawanDao.insert(karyawan)
            karyawanRef.child(karyawan.namaKaryawan).setValue(karyawan).await()
            Log.d("Repository", "Karyawan berhasil ditambahkan ke Firebase: $karyawan")
        }
        catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan karyawan: ${e.message}")
        }
    }

    suspend fun update(karyawan: Karyawan, oldNama: String) {
        try {
            karyawanDao.update(karyawan)
            if (karyawan.namaKaryawan != oldNama) {
                karyawanRef.child(oldNama).removeValue()
            }
            karyawanRef.child(karyawan.namaKaryawan).setValue(karyawan)
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui karyawan: ${e.message}")
        }
    }

    suspend fun delete(karyawan: Karyawan) {
        try {
            karyawanDao.delete(karyawan)
            karyawanRef.child(karyawan.namaKaryawan).removeValue().await()
            Log.d("Repository", "Karyawan berhasil dihapus dari Firebase: $karyawan")
        }
        catch (e: Exception) {
            Log.e("Repository", "Gagal menghapus karyawan: ${e.message}")
        }
    }
    fun getKaryawanById(karyawanId: Long): LiveData<Karyawan> {
        return karyawanDao.getKaryawanById(karyawanId.toString())
    }

}

