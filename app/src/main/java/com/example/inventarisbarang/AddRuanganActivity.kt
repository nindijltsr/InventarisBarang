package com.example.inventarisbarang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class AddRuanganActivity : AppCompatActivity() {

    private lateinit var editLokasi: EditText
    private lateinit var inventarisViewModel: InventarisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ruangan)

        editLokasi = findViewById(R.id.edit_lokasi)
        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val namaRuangan = editLokasi.text.toString()

            val ruangan = Ruangan(namaRuangan = namaRuangan)
            inventarisViewModel.insertRuangan(ruangan) // Updated to insertRuangan
            finish()
        }
    }
}
