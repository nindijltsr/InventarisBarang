package com.example.inventarisbarang.detailActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.inventarisbarang.MainActivity
import com.example.inventarisbarang.databinding.ActivityDetailBarangBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.Backend.InventarisViewModel

class DetailBarangActivity : AppCompatActivity() {

    // Deklarasi variabel untuk menghubungkan layout dengan kode
    private lateinit var binding: ActivityDetailBarangBinding

    // Deklarasi ViewModel untuk mengelola data barang, ruangan, dan karyawan
    private val inventarisViewModel: InventarisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan activity dengan layout activity_detail_barang menggunakan view binding
        binding = ActivityDetailBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan barang ID yang dikirim dari activity sebelumnya melalui Intent
        val barangId = intent.getLongExtra("BARANG_ID", 0)

        // Mengamati LiveData yang berisi data barang berdasarkan ID yang didapat
        inventarisViewModel.getBarangById(barangId).observe(this, Observer { barang ->
            barang?.let {
                // Jika data barang ditemukan, tampilkan detail barang di UI
                displayBarangDetails(it)
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
    private fun displayBarangDetails(barang: Barang) {
        // Mendapatkan data ruangan berdasarkan ruanganId dari barang
        inventarisViewModel.getRuanganById(barang.ruanganId).observe(this, Observer { ruangan ->
            ruangan?.let {
                // Menampilkan nama ruangan di TextView
                binding.textRuangan.text = "Ruangan: ${it.namaRuangan}"
            }
        })

        // Mendapatkan data karyawan berdasarkan karyawanId dari barang
        inventarisViewModel.getKaryawanById(barang.karyawanId).observe(this, Observer { karyawan ->
            karyawan?.let {
                // Menampilkan informasi karyawan yang bertanggung jawab di TextView
                binding.textKaryawan.text = "Penanggung jawab: ${it.namaKaryawan}"
                binding.textJabatanKaryawan.text = "Jabatan: ${it.jabatan}"
                binding.textKontakKaryawan.text = "Kontak: ${it.kontak}"
            }
        })

        // Menampilkan detail barang lainnya di UI
        binding.apply {

            // Log untuk membantu debugging dengan menampilkan nama barang yang sedang ditampilkan
            Log.d("DetailBarangActivity", "Menampilkan barang: ${barang.nama}")

            // Mengatur teks untuk setiap TextView dengan informasi dari objek barang
            textNama.text = "Nama Barang: ${barang.nama}"
            textKategori.text = "Kategori: ${barang.kategori}"
            textJumlah.text = "Jumlah: ${barang.jumlah}"
            textTanggalMasuk.text = "Tanggal Masuk: ${barang.tanggalMasuk}"
            textKondisi.text = "Kondisi: ${barang.kondisi}"
        }
    }
}
