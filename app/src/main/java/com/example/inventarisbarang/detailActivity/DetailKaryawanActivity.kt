package com.example.inventarisbarang.detailActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inventarisbarang.MainActivity
import com.example.inventarisbarang.databinding.ActivityDetailKaryawanBinding
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.Backend.InventarisViewModel

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
        val karyawanId = intent.getLongExtra("KARYAWAN_ID", 0)

        // Mengamati LiveData yang berisi data barang berdasarkan ID yang didapat
        inventarisViewModel.getKaryawanById(karyawanId).observe(this, Observer { karyawan ->
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
            textNama.text = "Nama Karyawan: ${karyawan.namaKaryawan}"
            textJabatan.text = "Jabatan: ${karyawan.jabatan}"
            textKontak.text = "Kontak: ${karyawan.kontak}"

        }
    }
}
