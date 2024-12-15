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

// EditBarangActivity adalah subclass dari AppCompatActivity yang digunakan untuk
// membuat UI activity untuk mengedit barang.
class EditBarangActivity : AppCompatActivity() {

    // inventarisViewModel: ViewModel untuk mengelola data Barang, Karyawan, dan Ruangan.
    private lateinit var inventarisViewModel: InventarisViewModel

    // Menyimpan ID barang yang akan diedit.
    private var barangId: Long = 0

    // Menyimpan data Barang yang akan diedit.
    private var barang: Barang? = null

    // List yang akan menampung data Ruangan dan Karyawan yang tersedia.
    private var ruanganList: List<Ruangan> = emptyList()
    private var karyawanList: List<Karyawan> = emptyList()

    // onCreate adalah lifecycle method yang dipanggil saat activity dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView mengatur layout activity_edit_barang sebagai tampilan untuk activity ini
        setContentView(R.layout.activity_edit_barang)

        // Menginisialisasi InventarisViewModel menggunakan ViewModelProvider
        // agar data dapat dikelola dengan cara yang terpisah dari UI.
        inventarisViewModel = ViewModelProvider(this)[InventarisViewModel::class.java]

        // Mendapatkan barangId yang dikirim melalui Intent untuk mengidentifikasi barang yang akan diedit.
        barangId = intent.getLongExtra("BARANG_ID", 0)

        // Observasi perubahan data Ruangan dan Karyawan. Saat data berubah, list ruangan dan karyawan akan diperbarui.
        inventarisViewModel.allRuangan.observe(this, Observer { ruangan ->
            ruanganList = ruangan
        })
        inventarisViewModel.allKaryawan.observe(this, Observer { karyawan ->
            karyawanList = karyawan
        })

        // Mengobservasi barang berdasarkan barangId. Ketika data barang ditemukan,
        // dialog edit barang akan ditampilkan dengan memanggil showEditDialog()
        inventarisViewModel.getBarangById(barangId).observe(this, Observer { barang ->

            // Method ini digunakan untuk menampilkan dialog yang berisi form edit barang.
            this.barang = barang
            barang?.let {
                showEditDialog(it)
            }
        })
    }

//  Method ini digunakan untuk menampilkan dialog yang berisi form edit barang
    private fun showEditDialog(barang: Barang) {
        // layoutInflater.inflate() digunakan untuk mengambil layout activity_edit_barang dan menampilkannya dalam dialog.
        val dialogView = layoutInflater.inflate(R.layout.activity_edit_barang, null)

        // Menginisialisasi komponen EditText dan Spinner dari layout dialog untuk mengisi data barang yang akan diedit.
        val editNama: EditText = dialogView.findViewById(R.id.edit_nama)
        val editKategori: EditText = dialogView.findViewById(R.id.edit_kategori)
        val editJumlah: EditText = dialogView.findViewById(R.id.edit_jumlah)
        val editTanggalMasuk: EditText = dialogView.findViewById(R.id.edit_tanggal_masuk)
        val editKondisi: EditText = dialogView.findViewById(R.id.edit_kondisi)
        val spinnerRuangan: Spinner = dialogView.findViewById(R.id.spinner_ruangan)
        val spinnerKaryawan: Spinner = dialogView.findViewById(R.id.spinner_karyawan)

        // Mengisi nilai dari data Barang ke komponen EditText agar pengguna dapat melihat dan mengeditnya.
        editNama.setText(barang.nama)
        editKategori.setText(barang.kategori)
        editJumlah.setText(barang.jumlah.toString())
        editTanggalMasuk.setText(barang.tanggalMasuk)
        editKondisi.setText(barang.kondisi)

        // Membuat ArrayAdapter untuk mengisi Spinner dengan data dari ruanganList dan karyawanList.
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

        // Mengatur posisi Spinner agar sesuai dengan ID ruanganId dan karyawanId yang terkait dengan barang yang sedang diedit.
        val selectedKaryawanPosition = karyawanList.indexOfFirst { karyawan -> karyawan.id == barang.karyawanId }

        if (selectedKaryawanPosition != -1) {
            spinnerKaryawan.setSelection(selectedKaryawanPosition)
        }

        // AlertDialog.Builder digunakan untuk membuat dialog edit barang.
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Edit Barang")
            .setView(dialogView)

            // Saat tombol "Simpan" ditekan, data diambil dari EditText dan Spinner,
            // kemudian barang diperbarui menggunakan ViewModel dengan metode updateBarang().
            // setNegativeButton: Menutup dialog tanpa menyimpan perubahan.
            .setPositiveButton("Simpan") { _, _ ->
                val nama = editNama.text.toString()
                val kategori = editKategori.text.toString()
                val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
                val tanggalMasuk = editTanggalMasuk.text.toString()
                val kondisi = editKondisi.text.toString()
                val updatedBarang = barang.copy(
                    nama = nama,
                    kategori = kategori,
                    jumlah = jumlah,
                    tanggalMasuk = tanggalMasuk,
                    kondisi = kondisi,
                    ruanganId = ruanganList[spinnerRuangan.selectedItemPosition].id,
                    karyawanId = karyawanList[spinnerKaryawan.selectedItemPosition].id
                )
                inventarisViewModel.updateBarang(updatedBarang, oldNama = barang.nama)
                Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Kembali") { dialog, _ -> dialog.dismiss() }

        // Tampilkan dialog
        dialogBuilder.create().show()
    }
}
