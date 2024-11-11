package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inventarisbarang.databinding.ActivityDetailBarangBinding
import com.example.inventarisbarang.databinding.ActivityDetailKaryawanBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class DetailKaryawanActivity : AppCompatActivity() {

    // Deklarasi variabel untuk menghubungkan layout dengan kode
    private lateinit var binding: ActivityDetailKaryawanBinding

    // Deklarasi ViewModel untuk mengelola data barang, ruangan, dan karyawan
    private val inventarisViewModel: InventarisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan activity dengan layout activity_detail_barang menggunakan view binding
        binding = ActivityDetailKaryawanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan barang ID yang dikirim dari activity sebelumnya melalui Intent
        val barangId = intent.getLongExtra("BARANG_ID", 0)

        // Mengamati LiveData yang berisi data barang berdasarkan ID yang didapat
        inventarisViewModel.getKaryawanById(barangId).observe(this, Observer { karyawan ->
            karyawan?.let {
                // Jika data barang ditemukan, tampilkan detail barang di UI
                displayKaryawanDetails(it)
            }
        })

        // Menambahkan listener untuk tombol Back
        binding.buttonBack.setOnClickListener {
            // Membuat intent untuk berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Menutup activity ini agar pengguna tidak bisa kembali ke halaman detail setelah menekan tombol Back
            finish()
        }
    }

    // Fungsi untuk menampilkan detail barang di UI
    @SuppressLint("SetTextI18n")
    private fun displayKaryawanDetails(karyawan: Karyawan) {
        binding.apply {

            Log.d("DetailKaryawanActivity", "Menampilkan karyawan: ${karyawan.namaKaryawan}")

            // Mengatur teks untuk setiap TextView dengan informasi dari objek barang
            textNama.text = "Nama Barang: ${karyawan.namaKaryawan}"
            textJabatan.text = "Kategori: ${karyawan.jabatan}"
            textKontak.text = "Jumlah: ${karyawan.kontak}"

        }
    }
}
