package com.example.inventarisbarang

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inventarisbarang.databinding.ActivityDetailBarangBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class DetailBarangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBarangBinding
    private val inventarisViewModel: InventarisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan barang ID dari Intent
        val barangId = intent.getLongExtra("BARANG_ID", 0)

        // Observe LiveData untuk barang yang sesuai berdasarkan ID
        inventarisViewModel.getBarangById(barangId).observe(this, Observer { barang ->
            barang?.let { displayBarangDetails(it) }
        })
    }

    // Fungsi untuk menampilkan detail barang di UI
    private fun displayBarangDetails(barang: Barang) {
        // Mendapatkan nama ruangan dan nama karyawan berdasarkan ID
        inventarisViewModel.getRuanganById(barang.ruanganId).observe(this, Observer { ruangan ->
            ruangan?.let { binding.textRuangan.text = it.namaRuangan }
        })

        inventarisViewModel.getKaryawanById(barang.karyawanId).observe(this, Observer { karyawan ->
            karyawan?.let { binding.textKaryawan.text = it.namaKaryawan }
        })

        // Menampilkan detail barang di UI
        binding.apply {
            textNama.text = barang.nama
            textKategori.text = barang.kategori
            textJumlah.text = barang.jumlah.toString()
            textTanggalMasuk.text = barang.tanggalMasuk
            textKondisi.text = barang.kondisi
        }
    }
}


