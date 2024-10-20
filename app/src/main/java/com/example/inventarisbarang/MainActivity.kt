package com.example.inventarisbarang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        // Initialize barangAdapter with required parameters
        barangAdapter = BarangAdapter { barang ->  // onItemClickListener for viewing details
            val intent = Intent(this, DetailBarangActivity::class.java).apply {
                putExtra("BARANG_ID", barang.id)
            }
            startActivity(intent)
        }


        // Set up RecyclerView with adapter and layout manager
        val recyclerView = binding.recyclerView
        recyclerView.adapter = barangAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel and observe LiveData
        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)
        inventarisViewModel.allBarang.observe(this, { barangs ->
            barangs?.let { barangAdapter.setBarang(it) }
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
