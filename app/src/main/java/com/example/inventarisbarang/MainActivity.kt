package com.example.inventarisbarang

import android.content.Intent
import android.os.Bundle
import android.util.Log // Tambahkan log untuk debugging
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventarisbarang.database.InventarisDatabase
import com.example.inventarisbarang.databinding.ActivityMainBinding
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inventarisViewModel: InventarisViewModel
    private lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = InventarisDatabase.getDatabase(this)

        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        barangAdapter = BarangAdapter({ barang ->
            val intent = Intent(this, DetailBarangActivity::class.java)
            intent.putExtra("barang_id", barang.id)
            startActivity(intent)
        }, inventarisViewModel)


        // Set up RecyclerView with adapter and layout manager
        val recyclerView = binding.recyclerView
        recyclerView.adapter = barangAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel and observe LiveData


        // Observe data barang dari ViewModel
        inventarisViewModel.allBarang.observe(this, { barangs ->
            if (barangs != null) {
                barangAdapter.setBarang(barangs)
                Log.d("MainActivity", "Data barang diobservasi: $barangs") // Debugging
            }
        })

        // Set up button listeners
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, AddBarangActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAddRuangan.setOnClickListener {
            val intent = Intent(this, AddRuanganActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAddKaryawan.setOnClickListener {
            val intent = Intent(this, AddKaryawanActivity::class.java)
            startActivity(intent)
        }
    }
}
