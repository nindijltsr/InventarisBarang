package com.example.inventarisbarang.Backend

import androidx.lifecycle.LiveData
import com.example.inventarisbarang.DAO.BarangDao
import com.example.inventarisbarang.DAO.KaryawanDao
import com.example.inventarisbarang.DAO.RuanganDao
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
            val barangList = barangRef.get().await().children
            val newId = (barangList.count() + 1).toLong()
            barang.id = newId
            barangDao.insert(barang)
            barangRef.child(barang.id.toString()).setValue(barang)
            Log.d("Repository", "Barang berhasil ditambahkan ke Firebase: $barang")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan barang: ${e.message}")
        }
    }

    suspend fun update(barang: Barang) {
        try {
            barangDao.update(barang)
            barangRef.child(barang.id.toString()).setValue(barang)
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




    //Ruangan CRUD
    suspend fun insert(ruangan: Ruangan) {
        try {
            val ruanganList = ruanganRef.get().await().children
            val newId = (ruanganList.count() + 1).toLong() // Set ID locally (for Room)

            ruangan.id = newId

            ruanganDao.insert(ruangan)
            ruanganRef.child(ruangan.id.toString()).setValue(ruangan)
            Log.d("Repository", "Ruangan berhasil ditambahkan ke Firebase: $ruangan")
        } catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan ruangan: ${e.message}")

        }
    }

    suspend fun update(ruangan: Ruangan) {
        try {
            ruanganDao.update(ruangan)
            ruanganRef.child(ruangan.id.toString()).setValue(ruangan)
        } catch (e: Exception) {
            Log.e("Repository", "Gagal memperbarui karyawan: ${e.message}")
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




    //KARYAWAN CRUD
    suspend fun insert(karyawan: Karyawan) {
        try {
            val karyawanList = karyawanRef.get().await().children
            val newId = (karyawanList.count() + 1).toLong() // Set ID locally (for Room)

            karyawan.id = newId

            karyawanDao.insert(karyawan)
            karyawanRef.child(karyawan.id.toString()).setValue(karyawan).await()
            Log.d("Repository", "Karyawan berhasil ditambahkan ke Firebase: $karyawan")
        }
        catch (e: Exception) {
            Log.e("Repository", "Gagal menambahkan karyawan: ${e.message}")
        }
    }

    suspend fun update(karyawan: Karyawan) {
        try {
            karyawanDao.update(karyawan)
            karyawanRef.child(karyawan.id.toString()).setValue(karyawan)
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

