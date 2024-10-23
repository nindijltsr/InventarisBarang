package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // Set listener untuk tombol Next
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: menutup HomeActivity agar tidak bisa kembali ke halaman ini
        }
    }

    // Fungsi untuk menampilkan detail barang di UI
    @SuppressLint("SetTextI18n")
    private fun displayBarangDetails(barang: Barang) {
        // Mendapatkan nama ruangan dan nama karyawan berdasarkan ID
        inventarisViewModel.getRuanganById(barang.ruanganId).observe(this, Observer { ruangan ->
            ruangan?.let {
                binding.textRuangan.text = "Ruangan : ${it.namaRuangan}"
            }
        })

        inventarisViewModel.getKaryawanById(barang.karyawanId).observe(this, Observer { karyawan ->
            karyawan?.let {
                binding.textKaryawan.text = "Penanggung jawab : ${it.namaKaryawan}"
                binding.textJabatanKaryawan.text = "Jabatan : ${it.jabatan}"
                binding.textKontakKaryawan.text = "Kontak : ${it.kontak}"
            }
        })

        // Menampilkan detail barang di UI
        binding.apply {
            Log.d("DetailBarangActivity", "Menampilkan barang: ${barang.nama}")
            textNama.text = "Nama Barang : ${barang.nama} "
            textKategori.text = "Kategori : ${barang.kategori}"
            textJumlah.text = "Jumlah: ${barang.jumlah}"
            textTanggalMasuk.text = "Tanggal Masuk: ${barang.tanggalMasuk}"
            textKondisi.text = "Kondisi: ${barang.kondisi}"
        }
    }
}
