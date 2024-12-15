package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

import com.example.inventarisbarang.viewmodel.InventarisViewModel

// EditBarangActivity adalah subclass dari AppCompatActivity yang digunakan untuk
// membuat UI activity untuk mengedit barang.
class EditRuanganActivity : AppCompatActivity() {

    // inventarisViewModel: ViewModel untuk mengelola data Barang, Karyawan, dan Ruangan.
    private lateinit var inventarisViewModel: InventarisViewModel

    // Menyimpan ID barang yang akan diedit.
    private var ruanganId: Long = 0

    // Menyimpan data Barang yang akan diedit.
    private var ruangan: Ruangan? = null

    // onCreate adalah lifecycle method yang dipanggil saat activity dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_karyawan)
        inventarisViewModel = ViewModelProvider(this)[InventarisViewModel::class.java]

        ruanganId = intent.getLongExtra("RUANGAN_ID", 0)

        inventarisViewModel.getRuanganById(ruanganId).observe(this, Observer { ruangan ->

            // Method ini digunakan untuk menampilkan dialog yang berisi form edit barang.
            this.ruangan = ruangan
            ruangan?.let {
                showEditDialog(it)
            }
        })
    }

    //  Method ini digunakan untuk menampilkan dialog yang berisi form edit barang
    @SuppressLint("MissingInflatedId")
    private fun showEditDialog(ruangan: Ruangan) {
        // layoutInflater.inflate() digunakan untuk mengambil layout activity_edit_barang dan menampilkannya dalam dialog.
        val dialogView = layoutInflater.inflate(R.layout.activity_edit_ruangan, null)

        // Menginisialisasi komponen EditText dan Spinner dari layout dialog untuk mengisi data barang yang akan diedit.
        val editNama: EditText = dialogView.findViewById(R.id.edit_nama_karyawan)

        // Mengisi nilai dari data Barang ke komponen EditText agar pengguna dapat melihat dan mengeditnya.
        editNama.setText(ruangan.namaRuangan)

        // AlertDialog.Builder digunakan untuk membuat dialog edit barang.
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Edit Ruangan")
            .setView(dialogView)

            .setPositiveButton("Simpan") { _, _ ->
                val nama = editNama.text.toString()
                val updatedRuangan = ruangan.copy(
                    namaRuangan = nama
                )
                inventarisViewModel.updateRuangan(updatedRuangan, oldNama = ruangan.namaRuangan)
                Toast.makeText(this, "Ruangan berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Kembali") { dialog, _ -> dialog.dismiss() }

        // Tampilkan dialog
        dialogBuilder.create().show()
    }
}
