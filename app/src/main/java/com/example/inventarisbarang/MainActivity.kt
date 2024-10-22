package com.example.inventarisbarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventarisbarang.database.InventarisDatabase
import com.example.inventarisbarang.databinding.ActivityMainBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
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
            intent.putExtra("BARANG_ID", barang.id)
            startActivity(intent)
        }, inventarisViewModel)

        // Set up RecyclerView with adapter and layout manager
        val recyclerView = binding.recyclerView
        recyclerView.adapter = barangAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe data barang dari ViewModel
        inventarisViewModel.allBarang.observe(this, { barangs ->
            if (barangs != null) {
                barangAdapter.setBarang(barangs)
                Log.d("MainActivity", "Data barang diobservasi: $barangs")
            }
        })

        // Set up button listeners
        binding.buttonAdd.setOnClickListener {
            showAddBarangDialog()
        }

        binding.buttonAddRuangan.setOnClickListener {
            showAddRuanganDialog()
        }

        binding.buttonAddKaryawan.setOnClickListener {
            showAddKaryawanDialog()
        }
    }

    private fun showAddBarangDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_barang, null)

        val spinnerRuangan = dialogView.findViewById<Spinner>(R.id.spinner_ruangan)
        val spinnerKaryawan = dialogView.findViewById<Spinner>(R.id.spinner_karyawan)

        // Mengisi spinnerRuangan dengan data Ruangan
        inventarisViewModel.allRuangan.observe(this, { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter
        })

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(this, { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Barang")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val editNama = dialogView.findViewById<EditText>(R.id.edit_nama)
            val editKategori = dialogView.findViewById<EditText>(R.id.edit_kategori)
            val editJumlah = dialogView.findViewById<EditText>(R.id.edit_jumlah)
            val editTanggalMasuk = dialogView.findViewById<EditText>(R.id.edit_tanggal_masuk)
            val editKondisi = dialogView.findViewById<EditText>(R.id.edit_kondisi)

            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

            val ruanganId = inventarisViewModel.allRuangan.value?.get(spinnerRuangan.selectedItemPosition)?.id ?: 0
            val karyawanId = inventarisViewModel.allKaryawan.value?.get(spinnerKaryawan.selectedItemPosition)?.id ?: 0

            val barang = Barang(
                nama = nama,
                kategori = kategori,
                jumlah = jumlah,
                tanggalMasuk = tanggalMasuk,
                kondisi = kondisi,
                ruanganId = ruanganId,
                karyawanId = karyawanId
            )

            inventarisViewModel.insertBarang(barang)
            Toast.makeText(this, "Barang added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showAddKaryawanDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_karyawan, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Karyawan")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val editNama = dialogView.findViewById<EditText>(R.id.edit_namaKaryawan)
            val editJabatan = dialogView.findViewById<EditText>(R.id.edit_jabatan)
            val editKontak = dialogView.findViewById<EditText>(R.id.edit_kontak)

            val nama = editNama.text.toString()
            val jabatan = editJabatan.text.toString()
            val kontak = editKontak.text.toString()

            val karyawan = Karyawan(namaKaryawan = nama, jabatan = jabatan, kontak = kontak)
            inventarisViewModel.insertKaryawan(karyawan)
            Toast.makeText(this, "Karyawan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showAddRuanganDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_ruangan, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Ruangan")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val editLokasi = dialogView.findViewById<EditText>(R.id.edit_lokasi)

            val namaRuangan = editLokasi.text.toString()

            val ruangan = Ruangan(namaRuangan = namaRuangan)
            inventarisViewModel.insertRuangan(ruangan)
            Toast.makeText(this, "Ruangan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}
