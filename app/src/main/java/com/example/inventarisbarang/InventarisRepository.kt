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
            // Generate unique ID for Firebase
            val barangList = barangRef.get().await().children
            val newId = (barangList.count() + 1).toLong() // Set ID locally (for Room)

            barang.id = newId

            // Insert into Room database
            barangDao.insert(barang)

            // Insert into Firebase
            barangRef.child(newId.toString()).setValue(barang).await()
            Log.d("Repository", "Barang berhasil ditambahkan ke Firebase: $barang")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan barang: ${e.message}")
        }
    }

    suspend fun update(barang: Barang) {
        try {
            // Update locally
            barangDao.update(barang)

            // Update Firebase
            barangRef.child(barang.id.toString()).setValue(barang).await()
            Log.d("Repository", "Barang berhasil diperbarui di Firebase: $barang")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui barang: ${e.message}")
        }
    }

    suspend fun delete(barang: Barang) {
        try {
            // Delete locally
            barangDao.delete(barang)

            // Delete from Firebase
            barangRef.child(barang.id.toString()).removeValue().await()
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
            val ruanganList = ruanganRef.get().await().children
            val newId = (ruanganList.count() + 1).toLong() // Set ID locally (for Room)

            ruangan.id = newId

            ruanganDao.insert(ruangan)
            ruanganRef.child(newId.toString()).setValue(ruangan).await()
            Log.d("Repository", "Ruangan berhasil ditambahkan ke Firebase: $ruangan")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan ruangan: ${e.message}")

        }
    }

    suspend fun update(ruangan: Ruangan) {
        try {
            ruanganDao.update(ruangan)
            ruanganRef.child(ruangan.id.toString()).setValue(ruangan).await()
            Log.d("Repository", "Ruangan berhasil diperbarui di Firebase: $ruangan")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui ruangan: ${e.message}")

        }
    }

    suspend fun delete(ruangan: Ruangan) {
        try {
            ruanganDao.delete(ruangan)
            ruanganRef.child(ruangan.id.toString()).removeValue().await()
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
            val karyawanList = karyawanRef.get().await().children
            val newId = (karyawanList.count() + 1).toLong() // Set ID locally (for Room)

            karyawan.id = newId

            karyawanDao.insert(karyawan)
            karyawanRef.child(newId.toString()).setValue(karyawan).await()
            Log.d("Repository", "Karyawan berhasil ditambahkan ke Firebase: $karyawan")
        }
        catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan karyawan: ${e.message}")
        }
    }

    suspend fun update(karyawan: Karyawan) {
        try {
            karyawanDao.update(karyawan)
            karyawanRef.child(karyawan.id.toString()).setValue(karyawan).await()
            Log.d("Repository", "Karyawan berhasil diperbarui di Firebase: $karyawan")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui karyawan: ${e.message}")
        }
    }

    suspend fun delete(karyawan: Karyawan) {
        try {
            karyawanDao.delete(karyawan)
            karyawanRef.child(karyawan.id.toString()).removeValue().await()
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
