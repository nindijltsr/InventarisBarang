package com.example.inventarisbarang.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventarisbarang.InventarisRepository
import com.example.inventarisbarang.database.InventarisDatabase
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

import kotlinx.coroutines.launch

class InventarisViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InventarisRepository
    val allBarang: LiveData<List<Barang>>
    val allRuangan: LiveData<List<Ruangan>>
    val allKaryawan: LiveData<List<Karyawan>>

    init {
        val barangDao = InventarisDatabase.getDatabase(application).barangDao()
        val ruanganDao = InventarisDatabase.getDatabase(application).ruanganDao()
        val karyawanDao = InventarisDatabase.getDatabase(application).karyawanDao()
        repository = InventarisRepository(barangDao, ruanganDao, karyawanDao)
        allBarang = repository.allBarang
        allRuangan = repository.allRuangan
        allKaryawan = repository.allKaryawan
    }

    // Barang
    fun insertBarang(barang: Barang) = viewModelScope.launch {
        try {
            repository.insert(barang) // Inserts into Room and Firebase
            Log.d("InventarisViewModel", "Barang disimpan: $barang")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menyimpan barang: ${e.message}")
        }
    }

    fun updateBarang(barang: Barang) = viewModelScope.launch {
        try {
            repository.update(barang) // Updates both Room and Firebase
            Log.d("InventarisViewModel", "Barang diperbarui: $barang")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal memperbarui barang: ${e.message}")
        }
    }

    fun deleteBarang(barang: Barang) = viewModelScope.launch {
        try {
            repository.delete(barang) // Deletes from Room and Firebase
            Log.d("InventarisViewModel", "Barang dihapus: $barang")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menghapus barang: ${e.message}")
        }
    }

    fun getBarangById(barangId: Long): LiveData<Barang> {
        Log.d("InventarisViewModel", "Mengambil barang dengan ID: $barangId")
        return repository.getBarangById(barangId)
    }

    // Ruangan
    fun insertRuangan(ruangan: Ruangan) = viewModelScope.launch {
        try {
            repository.insert(ruangan) // Inserts into Room and Firebase
            Log.d("InventarisViewModel", "Ruangan disimpan: $ruangan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menyimpan ruangan: ${e.message}")
        }
    }

    fun updateRuangan(ruangan: Ruangan) = viewModelScope.launch {
        try {
            repository.update(ruangan) // Updates both Room and Firebase
            Log.d("InventarisViewModel", "Ruangan diperbarui: $ruangan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal memperbarui ruangan: ${e.message}")
        }
    }

    fun deleteRuangan(ruangan: Ruangan) = viewModelScope.launch {
        try {
            repository.delete(ruangan) // Deletes from Room and Firebase
            Log.d("InventarisViewModel", "Ruangan dihapus: $ruangan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menghapus ruangan: ${e.message}")
        }
    }

    fun getRuanganById(ruanganId: Long): LiveData<Ruangan> {
        return repository.getRuanganById(ruanganId)
    }

    // Karyawan
    fun insertKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        try {
            repository.insert(karyawan) // Inserts into Room and Firebase
            Log.d("InventarisViewModel", "Karyawan disimpan: $karyawan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menyimpan karyawan: ${e.message}")
        }
    }

    fun deleteKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        try {
            repository.delete(karyawan) // Deletes from Room and Firebase
            Log.d("InventarisViewModel", "Karyawan dihapus: $karyawan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal menghapus karyawan: ${e.message}")
        }
    }

    fun updateKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        try {
            repository.update(karyawan) // Updates both Room and Firebase
            Log.d("InventarisViewModel", "Karyawan diperbarui: $karyawan")
        } catch (e: Exception) {
            Log.e("InventarisViewModel", "Gagal memperbarui karyawan: ${e.message}")
        }
    }

    fun getKaryawanById(karyawanId: Long): LiveData<Karyawan> {
        return repository.getKaryawanById(karyawanId)
    }
}