package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class EditBarangActivity : AppCompatActivity() {

    private lateinit var inventarisViewModel: InventarisViewModel
    private var barangId: Long = 0
    private var barang: Barang? = null
    private var ruanganList: List<Ruangan> = emptyList()
    private var karyawanList: List<Karyawan> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_barang)

        // Inisialisasi ViewModel
        inventarisViewModel = ViewModelProvider(this)[InventarisViewModel::class.java]

        // Mendapatkan barangId dari intent
        barangId = intent.getLongExtra("BARANG_ID", 0)

        // Observasi data ruangan dan karyawan
        inventarisViewModel.allRuangan.observe(this, Observer { ruangan ->
            ruanganList = ruangan
        })

        inventarisViewModel.allKaryawan.observe(this, Observer { karyawan ->
            karyawanList = karyawan
        })

        // Observasi data barang berdasarkan ID
        inventarisViewModel.getBarangById(barangId).observe(this, Observer { barang ->
            this.barang = barang
            barang?.let {
                // Tampilkan dialog saat aktivitas dimuat
                showEditDialog(it)
            }
        })
    }

    private fun showEditDialog(barang: Barang) {
        // Inflate layout dialog
        val dialogView = layoutInflater.inflate(R.layout.activity_edit_barang, null)

        // Inisialisasi EditText dan Spinner di dialog
        val editNama: EditText = dialogView.findViewById(R.id.edit_nama)
        val editKategori: EditText = dialogView.findViewById(R.id.edit_kategori)
        val editJumlah: EditText = dialogView.findViewById(R.id.edit_jumlah)
        val editTanggalMasuk: EditText = dialogView.findViewById(R.id.edit_tanggal_masuk)
        val editKondisi: EditText = dialogView.findViewById(R.id.edit_kondisi)
        val spinnerRuangan: Spinner = dialogView.findViewById(R.id.spinner_ruangan)
        val spinnerKaryawan: Spinner = dialogView.findViewById(R.id.spinner_karyawan)

        // Set data barang ke EditText
        editNama.setText(barang.nama)
        editKategori.setText(barang.kategori)
        editJumlah.setText(barang.jumlah.toString())
        editTanggalMasuk.setText(barang.tanggalMasuk)
        editKondisi.setText(barang.kondisi)

        // Set adapter untuk spinner ruangan
        val ruanganAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ruanganList.map { it.namaRuangan })
        ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRuangan.adapter = ruanganAdapter

        // Set adapter untuk spinner karyawan
        val karyawanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, karyawanList.map { it.namaKaryawan })
        karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKaryawan.adapter = karyawanAdapter

        // Set posisi spinner sesuai dengan ID
        val selectedRuanganPosition = ruanganList.indexOfFirst { ruangan -> ruangan.id == barang.ruanganId }
        if (selectedRuanganPosition != -1) {
            spinnerRuangan.setSelection(selectedRuanganPosition)
        }

        val selectedKaryawanPosition = karyawanList.indexOfFirst { karyawan -> karyawan.id == barang.karyawanId }
        if (selectedKaryawanPosition != -1) {
            spinnerKaryawan.setSelection(selectedKaryawanPosition)
        }

        // Buat AlertDialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Edit Barang")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = editNama.text.toString()
                val kategori = editKategori.text.toString()
                val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
                val tanggalMasuk = editTanggalMasuk.text.toString()
                val kondisi = editKondisi.text.toString()

                // Update barang
                val updatedBarang = barang.copy(
                    nama = nama,
                    kategori = kategori,
                    jumlah = jumlah,
                    tanggalMasuk = tanggalMasuk,
                    kondisi = kondisi,
                    ruanganId = ruanganList[spinnerRuangan.selectedItemPosition].id,
                    karyawanId = karyawanList[spinnerKaryawan.selectedItemPosition].id
                )
                inventarisViewModel.updateBarang(updatedBarang)
                Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Kembali") { dialog, _ -> dialog.dismiss() }

        // Tampilkan dialog
        dialogBuilder.create().show()
    }
}
