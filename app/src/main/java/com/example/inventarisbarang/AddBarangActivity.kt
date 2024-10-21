package com.example.inventarisbarang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

@Suppress("UNREACHABLE_CODE")
class AddBarangActivity : AppCompatActivity() {

    private lateinit var inventarisViewModel: InventarisViewModel

    // Variabel untuk komponen UI
    private lateinit var editNama: EditText
    private lateinit var editKategori: EditText
    private lateinit var editJumlah: EditText
    private lateinit var editTanggalMasuk: EditText
    private lateinit var editKondisi: EditText
    private lateinit var spinnerRuangan: Spinner
    private lateinit var spinnerKaryawan: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_barang)

        // Menghubungkan komponen UI dengan ID-nya
        editNama = findViewById(R.id.edit_nama)
        editKategori = findViewById(R.id.edit_kategori)
        editJumlah = findViewById(R.id.edit_jumlah)
        editTanggalMasuk = findViewById(R.id.edit_tanggal_masuk)
        editKondisi = findViewById(R.id.edit_kondisi)
        spinnerRuangan = findViewById(R.id.spinner_ruangan)
        spinnerKaryawan = findViewById(R.id.spinner_karyawan)

        // Inisialisasi ViewModel
        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        // Tombol "Save" untuk menyimpan data barang
        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toInt()
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

            val barang = Barang(
                nama = nama,
                kategori = kategori,
                jumlah = jumlah,
                tanggalMasuk = tanggalMasuk,
                kondisi = kondisi,
                ruanganId = spinnerRuangan.selectedItemPosition,  // Ganti dengan ID ruangan dari spinner
                karyawanId = spinnerKaryawan.selectedItemPosition
            )

            // Memanggil insertBarang dari instance ViewModel
            inventarisViewModel.insertBarang(barang)
            finish() // Selesai dan kembali ke activity sebelumnya
        }
    }
}
