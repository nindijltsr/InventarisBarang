package com.example.inventarisbarang

import android.os.Bundle
import androidx.lifecycle.Observer
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

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

    private var isUpdate: Boolean = false
    private var barangId: Long? = null

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

        // Mengisi spinnerRuangan dengan data Ruangan
        inventarisViewModel.allRuangan.observe(this, Observer { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter
        })

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(this, Observer { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter
        })

        // Mendapatkan data dari intent jika ada
        val intent = intent
        if (intent.hasExtra("barangId")) {
            isUpdate = true
            barangId = intent.getLongExtra("barangId", -1)
            if (barangId != -1L) {
                loadBarangData(barangId!!)
            }
        }

        // Tombol "Save" untuk menyimpan atau mengupdate data barang
        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            if (isUpdate) {
                updateBarang()
            } else {
                saveBarang()
            }
        }
    }

    private fun loadBarangData(barangId: Long) {
        inventarisViewModel.getBarangById(barangId).observe(this) { barang ->
            // Pastikan barang tidak null sebelum diakses
            barang?.let {
                editNama.setText(it.nama)
                editKategori.setText(it.kategori)
                editJumlah.setText(it.jumlah.toString())
                editTanggalMasuk.setText(it.tanggalMasuk)
                editKondisi.setText(it.kondisi)
                // Pastikan ID ruangan dan karyawan sesuai dengan index spinner
                spinnerRuangan.setSelection(inventarisViewModel.allRuangan.value?.indexOfFirst { ruangan -> ruangan.id == it.ruanganId } ?: 0)
                spinnerKaryawan.setSelection(inventarisViewModel.allKaryawan.value?.indexOfFirst { karyawan -> karyawan.id == it.karyawanId } ?: 0)
            }
        }
    }

    private fun saveBarang() {
        val nama = editNama.text.toString()
        val kategori = editKategori.text.toString()
        val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
        val tanggalMasuk = editTanggalMasuk.text.toString()
        val kondisi = editKondisi.text.toString()

        val ruanganId = inventarisViewModel.allRuangan.value?.get(spinnerRuangan.selectedItemPosition)?.id ?: 0
        val karyawanId = inventarisViewModel.allKaryawan.value?.get(spinnerKaryawan.selectedItemPosition)?.id ?: 0

        val barang = Barang(
            nama = nama,
            kategori = kategori,
            jumlah = jumlah,
            tanggalMasuk = tanggalMasuk,
            kondisi = kondisi,
            ruanganId = ruanganId,
            karyawanId = karyawanId
        )

        inventarisViewModel.insertBarang(barang)
        finish() // Selesai dan kembali ke activity sebelumnya
    }

    private fun updateBarang() {
        barangId?.let {
            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

            val ruanganId = inventarisViewModel.allRuangan.value?.get(spinnerRuangan.selectedItemPosition)?.id ?: 0
            val karyawanId = inventarisViewModel.allKaryawan.value?.get(spinnerKaryawan.selectedItemPosition)?.id ?: 0

            val barang = Barang(
                nama = nama,
                kategori = kategori,
                jumlah = jumlah,
                tanggalMasuk = tanggalMasuk,
                kondisi = kondisi,
                ruanganId = ruanganId,
                karyawanId = karyawanId
            )

            inventarisViewModel.updateBarang(barang)
            finish() // Selesai dan kembali ke activity sebelumnya
        }
    }
}
