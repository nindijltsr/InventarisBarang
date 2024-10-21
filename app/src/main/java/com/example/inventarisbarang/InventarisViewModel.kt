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
        repository.insert(barang)
        Log.d("InventarisViewModel", "Barang disimpan: $barang")
    }

    fun updateBarang(barang: Barang) = viewModelScope.launch {
        repository.update(barang)
    }

    fun deleteBarang(barang: Barang) = viewModelScope.launch {
        repository.delete(barang)
    }

    fun getBarangById(barangId: Int): LiveData<Barang> {
        Log.d("InventarisViewModel", "Mengambil barang dengan ID: $barangId")
        return repository.getBarangById(barangId)
    }

    // Ruangan
    fun insertRuangan(ruangan: Ruangan) = viewModelScope.launch {
        repository.insert(ruangan)
    }

    fun updateRuangan(ruangan: Ruangan) = viewModelScope.launch {
        repository.update(ruangan)
    }

    fun deleteRuangan(ruangan: Ruangan) = viewModelScope.launch {
        repository.delete(ruangan)
    }

    fun getRuanganById(ruanganId: Int): LiveData<Ruangan> {
        return repository.getRuanganById(ruanganId)
    }

    // Karyawan
    fun insertKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        repository.insert(karyawan)
    }

    fun updateKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        repository.update(karyawan)
    }

    fun deleteKaryawan(karyawan: Karyawan) = viewModelScope.launch {
        repository.delete(karyawan)
    }

    fun getKaryawanById(karyawanId: Int): LiveData<Karyawan> {
        return repository.getKaryawanById(karyawanId)
    }
}
