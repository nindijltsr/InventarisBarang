package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class EditBarangActivity : AppCompatActivity() {

    private lateinit var editNama: EditText
    private lateinit var editKategori: EditText
    private lateinit var editJumlah: EditText
    private lateinit var editTanggalMasuk: EditText
    private lateinit var editKondisi: EditText
    private lateinit var spinnerRuangan: Spinner
    private lateinit var spinnerKaryawan: Spinner
    private lateinit var inventarisViewModel: InventarisViewModel
    private var barangId: Int = 0
    private var barang: Barang? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_barang)

        // Inisialisasi ViewModel
        inventarisViewModel = ViewModelProvider(this)[InventarisViewModel::class.java]

        editNama = findViewById(R.id.edit_nama)
        editKategori = findViewById(R.id.edit_kategori)
        editJumlah = findViewById(R.id.edit_jumlah)
        editTanggalMasuk = findViewById(R.id.edit_tanggal_masuk)
        editKondisi = findViewById(R.id.edit_kondisi)
        spinnerRuangan = findViewById(R.id.spinner_ruangan)
        spinnerKaryawan = findViewById(R.id.spinner_karyawan)

        barangId = intent.getIntExtra("BARANG_ID", 0)

        // Observasi data barang berdasarkan ID
        inventarisViewModel.getBarangById(barangId).observe(this, Observer { barang ->
            barang?.let {
                editNama.setText(it.nama)
                editKategori.setText(it.kategori)
                editJumlah.setText(it.jumlah.toString())
                editTanggalMasuk.setText(it.tanggalMasuk)
                editKondisi.setText(it.kondisi)
                this.barang = it
            }
        })

        // Tombol Simpan
        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toInt()
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

            val updatedBarang = barang?.copy(
                nama = nama,
                kategori = kategori,
                jumlah = jumlah,
                tanggalMasuk = tanggalMasuk,
                kondisi = kondisi
            )

            updatedBarang?.let {
                inventarisViewModel.updateBarang(it)
                Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman sebelumnya setelah penyimpanan
            }
        }

        // Tombol Hapus
        val buttonDelete = findViewById<Button>(R.id.button_delete)
        buttonDelete.setOnClickListener {
            barang?.let {
                inventarisViewModel.deleteBarang(it) // Menghapus barang dari database
                Toast.makeText(this, "Barang berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish() // Kembali setelah penghapusan
            }
        }
    }
}
