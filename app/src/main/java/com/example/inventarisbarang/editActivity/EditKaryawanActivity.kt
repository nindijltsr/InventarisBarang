package com.example.inventarisbarang.editActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.R
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.Backend.InventarisViewModel

// EditBarangActivity adalah subclass dari AppCompatActivity yang digunakan untuk
// membuat UI activity untuk mengedit barang.
class EditKaryawanActivity : AppCompatActivity() {

    // inventarisViewModel: ViewModel untuk mengelola data Barang, Karyawan, dan Ruangan.
    private lateinit var inventarisViewModel: InventarisViewModel

    // Menyimpan ID barang yang akan diedit.
    private var karyawanId: Long = 0

    // Menyimpan data Barang yang akan diedit.
    private var karyawan: Karyawan? = null

    // onCreate adalah lifecycle method yang dipanggil saat activity dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_karyawan)
        inventarisViewModel = ViewModelProvider(this)[InventarisViewModel::class.java]

        karyawanId = intent.getLongExtra("KARYAWAN_ID", 0)

        inventarisViewModel.getKaryawanById(karyawanId).observe(this, Observer { karyawan ->

            // Method ini digunakan untuk menampilkan dialog yang berisi form edit barang.
            this.karyawan = karyawan
            karyawan?.let {
                showEditDialog(it)
            }
        })
    }

    //  Method ini digunakan untuk menampilkan dialog yang berisi form edit barang
    @SuppressLint("MissingInflatedId")
    private fun showEditDialog(karyawan: Karyawan) {
        // layoutInflater.inflate() digunakan untuk mengambil layout activity_edit_barang dan menampilkannya dalam dialog.
        val dialogView = layoutInflater.inflate(R.layout.activity_edit_karyawan, null)

        // Menginisialisasi komponen EditText dan Spinner dari layout dialog untuk mengisi data barang yang akan diedit.
        val editNama: EditText = dialogView.findViewById(R.id.edit_nama_karyawan)
        val editJabatan: EditText = dialogView.findViewById(R.id.edit_jabatan)
        val editKontak: EditText = dialogView.findViewById(R.id.edit_kontak)

        // Mengisi nilai dari data Barang ke komponen EditText agar pengguna dapat melihat dan mengeditnya.
        editNama.setText(karyawan.namaKaryawan)
        editJabatan.setText(karyawan.jabatan)
        editKontak.setText(karyawan.kontak)

        // AlertDialog.Builder digunakan untuk membuat dialog edit barang.
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Edit Karyawan")
            .setView(dialogView)

            // Saat tombol "Simpan" ditekan, data diambil dari EditText dan Spinner,
            // kemudian barang diperbarui menggunakan ViewModel dengan metode updateBarang().
            // setNegativeButton: Menutup dialog tanpa menyimpan perubahan.
            .setPositiveButton("Simpan") { _, _ ->
                val nama = editNama.text.toString()
                val jabatan = editJabatan.text.toString()
                val kontak = editKontak.text.toString().toIntOrNull() ?: 0
                val updatedKaryawan = karyawan.copy(
                    namaKaryawan = nama,
                    jabatan = jabatan,
                    kontak = kontak.toString()
                )
                inventarisViewModel.updateKaryawan(updatedKaryawan)
                Toast.makeText(this, "Karyawan berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Kembali") { dialog, _ -> dialog.dismiss() }

        // Tampilkan dialog
        dialogBuilder.create().show()
    }
}
