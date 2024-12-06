package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.inventarisbarang.databinding.ActivityMainBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.viewmodel.InventarisViewModel
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inventarisViewModel: InventarisViewModel
    private lateinit var barangAdapter: BarangAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.DarkBlue)
        inventarisViewModel = ViewModelProvider(this).get(InventarisViewModel::class.java)

        barangAdapter = BarangAdapter(
            onItemClickListener = { barang ->
                showDetailBarangDialog(barang)
            },
            editClickListener = { barang ->
                showEditBarangDialog(barang)
            },
            karyawanEditClickListener = { karyawan ->
                showEditKaryawanDialog(karyawan)
            },
            karyawanItemClickListener = { karyawan ->
                showDetailKaryawanDialog(karyawan)
            },
            ruanganEditClickListener = { ruangan ->
                showEditRuanganDialog(ruangan)
            },
            viewModel = inventarisViewModel
        )

        // Set up RecyclerView with adapter and layout manager
        binding.recyclerView.apply {
            adapter = barangAdapter

            inventarisViewModel.allBarang.observe(this@MainActivity, { barangs ->
                inventarisViewModel.allKaryawan.observe(this@MainActivity, { karyawans ->
                    inventarisViewModel.allRuangan.observe(this@MainActivity, { ruanganList ->
                        val itemList = mutableListOf<Any>()
                        itemList.add("Daftar Barang")
                        itemList.addAll(barangs) // Menambahkan barang
                        itemList.add("Daftar Karyawan")
                        itemList.addAll(karyawans) // Menambahkan karyawan
                        itemList.add("Daftar Ruangan")
                        itemList.addAll(ruanganList) // Menambahkan ruangan

                        barangAdapter.submitList(itemList)

                        // Mengatur GridLayoutManager dengan SpanSizeLookup
                        val gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
                        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return when (itemList[position]) {
                                    "Daftar Barang", "Daftar Karyawan", "Daftar Ruangan" -> 2 // Header mengambil 2 kolom penuh
                                    else -> if (itemList[position] in ruanganList) 1 else 2 // Item ruangan 1 kolom, lainnya 2 kolom
                                }
                            }
                        }

                        layoutManager = gridLayoutManager
                    })
                })
            })
        }



        // Observe data barang dari ViewModel dan perbarui list

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

    @SuppressLint("MissingInflatedId")
    private fun showEditRuanganDialog(ruangan: Ruangan) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_edit_ruangan, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_ruangan)

        // Set data barang ke EditText
        editNama.setText(ruangan.namaRuangan)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Ruangan")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }


        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val nama = editNama.text.toString()

            if (nama.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val updatedRuangan = ruangan.copy(
                namaRuangan = nama
            )

            inventarisViewModel.updateRuangan(updatedRuangan)
            Toast.makeText(this, "Barang updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showDetailKaryawanDialog(karyawan: Karyawan) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_detail_karyawan, null)

        val textNama = dialogView.findViewById<TextView>(R.id.textNama)
        val textJabatan = dialogView.findViewById<TextView>(R.id.textJabatan)
        val textKontak = dialogView.findViewById<TextView>(R.id.textKontak)

        textNama.text = "Nama Karyawan: ${karyawan.namaKaryawan}"
        textJabatan.text = "Jabatan: ${karyawan.jabatan}"
        textKontak.text = "Kontak: ${karyawan.kontak}"

        val dialog = AlertDialog.Builder(this)
            .setTitle("Detail Karyawan")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditKaryawanDialog(karyawan: Karyawan) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_karyawan, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_karyawan)
        val editJabatan = dialogView.findViewById<EditText>(R.id.edit_jabatan)
        val editKontak = dialogView.findViewById<EditText>(R.id.edit_kontak)

        // Set data barang ke EditText
        editNama.setText(karyawan.namaKaryawan)
        editJabatan.setText(karyawan.jabatan)
        editKontak.setText(karyawan.kontak)


        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Karyawan")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }


        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val nama = editNama.text.toString()
            val jabatan = editJabatan.text.toString()
            val kontak = editKontak.text.toString()


            if (nama.isBlank() || jabatan.isBlank() || kontak.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val updatedKaryawan = karyawan.copy(
                namaKaryawan = nama,
                jabatan = jabatan,
                kontak = kontak
            )

            inventarisViewModel.updateKaryawan(updatedKaryawan)
            Toast.makeText(this, "Karyawan updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddBarangDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_barang, null)

        val spinnerRuangan = dialogView.findViewById<Spinner>(R.id.spinner_ruangan)
        val spinnerKaryawan = dialogView.findViewById<Spinner>(R.id.spinner_karyawan)

        // Mengisi spinnerRuangan dengan data Ruangan
        inventarisViewModel.allRuangan.observe(this, { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter
        })

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(this, { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Barang")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }

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

            val ruanganId =
                inventarisViewModel.allRuangan.value?.get(spinnerRuangan.selectedItemPosition)?.id
                    ?: 0
            val karyawanId =
                inventarisViewModel.allKaryawan.value?.get(spinnerKaryawan.selectedItemPosition)?.id
                    ?: 0

            if (nama.isBlank() || kategori.isBlank() || tanggalMasuk.isBlank() || kondisi.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


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

    @SuppressLint("MissingInflatedId")
    private fun showAddKaryawanDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_karyawan, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Karyawan")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_karyawan)
            val editJabatan = dialogView.findViewById<EditText>(R.id.edit_jabatan)
            val editKontak = dialogView.findViewById<EditText>(R.id.edit_kontak)

            val nama = editNama.text.toString()
            val jabatan = editJabatan.text.toString()
            val kontak = editKontak.text.toString()

            if (nama.isBlank() || jabatan.isBlank() || kontak.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val karyawan = Karyawan(namaKaryawan = nama, jabatan = jabatan, kontak = kontak)
            inventarisViewModel.insertKaryawan(karyawan)
            Toast.makeText(this, "Karyawan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddRuanganDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_ruangan, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Ruangan")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val editLokasi = dialogView.findViewById<EditText>(R.id.edit_lokasi)

            val namaRuangan = editLokasi.text.toString()

            if (namaRuangan.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ruangan = Ruangan(namaRuangan = namaRuangan)
            inventarisViewModel.insertRuangan(ruangan)
            Toast.makeText(this, "Ruangan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditBarangDialog(barang: Barang) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_barang, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama)
        val editKategori = dialogView.findViewById<EditText>(R.id.edit_kategori)
        val editJumlah = dialogView.findViewById<EditText>(R.id.edit_jumlah)
        val editTanggalMasuk = dialogView.findViewById<EditText>(R.id.edit_tanggal_masuk)
        val editKondisi = dialogView.findViewById<EditText>(R.id.edit_kondisi)
        val spinnerRuangan = dialogView.findViewById<Spinner>(R.id.spinner_ruangan)
        val spinnerKaryawan = dialogView.findViewById<Spinner>(R.id.spinner_karyawan)

        // Set data barang ke EditText
        editNama.setText(barang.nama)
        editKategori.setText(barang.kategori)
        editJumlah.setText(barang.jumlah.toString())
        editTanggalMasuk.setText(barang.tanggalMasuk)
        editKondisi.setText(barang.kondisi)

        // Mengisi spinnerRuangan dengan data Ruangan
        inventarisViewModel.allRuangan.observe(this, { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter

            // Set posisi spinner sesuai dengan ID
            val selectedRuanganPosition = ruanganList.indexOfFirst { it.id == barang.ruanganId }
            if (selectedRuanganPosition != -1) {
                spinnerRuangan.setSelection(selectedRuanganPosition)
            }
        })

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(this, { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter

            // Set posisi spinner sesuai dengan ID
            val selectedKaryawanPosition = karyawanList.indexOfFirst { it.id == barang.karyawanId }
            if (selectedKaryawanPosition != -1) {
                spinnerKaryawan.setSelection(selectedKaryawanPosition)
            }
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Barang")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }


        dialogView.findViewById<Button>(R.id.button_save).setOnClickListener {
            val nama = editNama.text.toString()
            val kategori = editKategori.text.toString()
            val jumlah = editJumlah.text.toString().toIntOrNull() ?: 0
            val tanggalMasuk = editTanggalMasuk.text.toString()
            val kondisi = editKondisi.text.toString()

            if (nama.isBlank() || kategori.isBlank() || tanggalMasuk.isBlank() || kondisi.isBlank()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ruanganId =
                inventarisViewModel.allRuangan.value?.get(spinnerRuangan.selectedItemPosition)?.id
                    ?: 0
            val karyawanId =
                inventarisViewModel.allKaryawan.value?.get(spinnerKaryawan.selectedItemPosition)?.id
                    ?: 0

            val updatedBarang = barang.copy(
                nama = nama,
                kategori = kategori,
                jumlah = jumlah,
                tanggalMasuk = tanggalMasuk,
                kondisi = kondisi,
                ruanganId = ruanganId,
                karyawanId = karyawanId
            )

            inventarisViewModel.updateBarang(updatedBarang)
            Toast.makeText(this, "Barang updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDetailBarangDialog(barang: Barang) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_detail_barang, null)

        // Mengisi data barang ke dialog
        val textNama = dialogView.findViewById<TextView>(R.id.textNama)
        val textKategori = dialogView.findViewById<TextView>(R.id.textKategori)
        val textJumlah = dialogView.findViewById<TextView>(R.id.textJumlah)
        val textTanggalMasuk = dialogView.findViewById<TextView>(R.id.textTanggalMasuk)
        val textKondisi = dialogView.findViewById<TextView>(R.id.textKondisi)
        val textRuangan = dialogView.findViewById<TextView>(R.id.textRuangan)
        val textKaryawan = dialogView.findViewById<TextView>(R.id.textKaryawan)
        val textJabatan = dialogView.findViewById<TextView>(R.id.textJabatanKaryawan)
        val textKontak = dialogView.findViewById<TextView>(R.id.textKontakKaryawan)


        // Mengisi data barang
        textNama.text = "Nama Barang: ${barang.nama}"
        textKategori.text = "Kategori: ${barang.kategori}"
        textJumlah.text = "Jumlah: ${barang.jumlah}"
        textTanggalMasuk.text = "Tanggal Masuk: ${barang.tanggalMasuk}"
        textKondisi.text = "Kondisi: ${barang.kondisi}"

        // Mengambil nama ruangan dan karyawan berdasarkan ID
        inventarisViewModel.getRuanganById(barang.ruanganId).observe(this, Observer { ruangan ->
            ruangan?.let {
                textRuangan.text = "Ruangan: ${it.namaRuangan}"
            }
        })

        inventarisViewModel.getKaryawanById(barang.karyawanId).observe(this, Observer { karyawan ->
            karyawan?.let {
                textKaryawan.text = "Penanggung Jawab: ${it.namaKaryawan}"
                textJabatan.text = "Jabatan: ${it.jabatan}"
                textKontak.text = "Kontak: ${it.kontak}"
            }
        })

        val dialog = AlertDialog.Builder(this)
            .setTitle("Detail Barang")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
