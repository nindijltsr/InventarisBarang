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
    private lateinit var karyawanAdapter: KaryawanAdapter
    private lateinit var ruanganAdapter: RuanganAdapter

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

        karyawanAdapter = KaryawanAdapter({ karyawan ->
            val intent = Intent(this, DetailKaryawanActivity::class.java)
            intent.putExtra("karyawan_id", karyawan.id)
            startActivity(intent)
        }, inventarisViewModel)

        ruanganAdapter = RuanganAdapter({ ruangan ->
            val intent = Intent(this, DetailRuanganActivity::class.java)
            intent.putExtra("ruangan_id", ruangan.id)
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

        inventarisViewModel.allKaryawan.observe(this, { karyawans ->
            if (karyawans != null) {
                karyawanAdapter.setKaryawan(karyawans)
                Log.d("MainActivity", "Data karyawan diobservasi: $karyawans") // Debugging
            }
        })

        inventarisViewModel.allRuangan.observe(this, { ruangan ->
            if (ruangan != null) {
                ruanganAdapter.setRuangan(ruangan)
                Log.d("MainActivity", "Data ruangan diobservasi: $ruangan") // Debugging
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
