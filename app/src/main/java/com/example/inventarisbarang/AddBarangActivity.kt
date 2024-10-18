package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddBarangActivity : AppCompatActivity() {
    private lateinit var editNama: EditText
    private lateinit var editKategori: EditText
    private lateinit var editJumlah: EditText
    private lateinit var editTanggalMasuk: EditText
    private lateinit var editKondisi: EditText
    private lateinit var spinnerRuangan: Spinner
    private lateinit var spinnerKaryawan: Spinner

    @SuppressLint("MissingInflatedId")
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

        // Tombol "Save" untuk menyimpan data barang
        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toInt()
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

        }
    }
}