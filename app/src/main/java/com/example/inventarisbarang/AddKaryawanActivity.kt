package com.example.inventarisbarang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Karyawan

class AddKaryawanActivity : AppCompatActivity() {

    private lateinit var editNama: EditText
    private lateinit var editJabatan: EditText
    private lateinit var editKontak: EditText
    private lateinit var inventarisViewModel: InventarisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_karyawan)

        editNama = findViewById(R.id.edit_nama)
        editJabatan = findViewById(R.id.edit_jabatan)
        editKontak = findViewById(R.id.edit_kontak)
        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val nama = editNama.text.toString()
            val posisi = editJabatan.text.toString()
            val kontak = editKontak.text.toString()

            val karyawan = Karyawan(nama = nama, posisi = posisi, kontak = kontak)
            inventarisViewModel.insertKaryawan(karyawan) // Updated to insertKaryawan
            finish()
        }
    }
}
