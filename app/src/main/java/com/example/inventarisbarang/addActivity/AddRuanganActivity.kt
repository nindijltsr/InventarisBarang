package com.example.inventarisbarang.addActivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.R
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.Backend.InventarisViewModel

class AddRuanganActivity : AppCompatActivity() {

    private lateinit var editLokasi: EditText
    private lateinit var inventarisViewModel: InventarisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ruangan)

        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        editLokasi = findViewById(R.id.edit_lokasi)

        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            val namaRuangan = editLokasi.text.toString()

            val ruangan = Ruangan(namaRuangan = namaRuangan)
            inventarisViewModel.insertRuangan(ruangan) // Updated to insertRuangan
            finish()
        }
    }
}
