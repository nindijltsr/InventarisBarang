package com.example.inventarisbarang.entityActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventarisbarang.Backend.InventarisViewModel
import com.example.inventarisbarang.BarangAdapter
import com.example.inventarisbarang.R
import com.example.inventarisbarang.databinding.BarangPageBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

class BarangFragment : Fragment() {
    private lateinit var binding: BarangPageBinding
    private lateinit var inventarisViewModel: InventarisViewModel
    private lateinit var barangAdapter: BarangAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BarangPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inventarisViewModel = ViewModelProvider(requireActivity()).get(InventarisViewModel::class.java)
        setupRecyclerView()

        binding.fabAddButton.setOnClickListener {
            showAddBarangDialog()
        }
    }

    private fun setupRecyclerView() {
        barangAdapter = BarangAdapter(
            onItemClickListener = {
                barang ->
                showDetailBarangDialog(barang) },
            editClickListener = {
                barang ->
                showEditBarangDialog(barang) },
            karyawanEditClickListener = {
                karyawan ->
                showEditKaryawanDialog(karyawan) },
            karyawanItemClickListener = {
                karyawan ->
                showDetailKaryawanDialog(karyawan) },
            ruanganEditClickListener = {
                ruangan ->
                showEditRuanganDialog(ruangan) },
            viewModel = inventarisViewModel
        )

        binding.recyclerView.apply {
            adapter = barangAdapter
            inventarisViewModel.allBarang.observe(viewLifecycleOwner, { barangs ->
                val itemList = mutableListOf<Any>()
                itemList.add("Daftar Barang")
                itemList.addAll(barangs)
                barangAdapter.submitList(itemList)
            })
            layoutManager = LinearLayoutManager(context)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddBarangDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_barang, null)

        val spinnerRuangan = dialogView.findViewById<Spinner>(R.id.spinner_ruangan)
        val spinnerKaryawan = dialogView.findViewById<Spinner>(R.id.spinner_karyawan)

        // Mengisi spinnerRuangan dengan data Ruangan
        inventarisViewModel.allRuangan.observe(viewLifecycleOwner) { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter
        }

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(viewLifecycleOwner) { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter
        }

        val dialog = AlertDialog.Builder(requireContext())
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "Barang added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditBarangDialog(barang: Barang) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_barang, null)

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
        inventarisViewModel.allRuangan.observe(viewLifecycleOwner) { ruanganList ->
            val ruanganNames = ruanganList.map { it.namaRuangan }
            val ruanganAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ruanganNames)
            ruanganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRuangan.adapter = ruanganAdapter

            // Set posisi spinner sesuai dengan ID
            val selectedRuanganPosition = ruanganList.indexOfFirst { it.id == barang.ruanganId }
            if (selectedRuanganPosition != -1) {
                spinnerRuangan.setSelection(selectedRuanganPosition)
            }
        }

        // Mengisi spinnerKaryawan dengan data Karyawan
        inventarisViewModel.allKaryawan.observe(viewLifecycleOwner) { karyawanList ->
            val karyawanNames = karyawanList.map { it.namaKaryawan }
            val karyawanAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, karyawanNames)
            karyawanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerKaryawan.adapter = karyawanAdapter

            // Set posisi spinner sesuai dengan ID
            val selectedKaryawanPosition = karyawanList.indexOfFirst { it.id == barang.karyawanId }
            if (selectedKaryawanPosition != -1) {
                spinnerKaryawan.setSelection(selectedKaryawanPosition)
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "Barang updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDetailBarangDialog(barang: Barang) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_detail_barang, null)

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
        inventarisViewModel.getRuanganById(barang.ruanganId).observe(viewLifecycleOwner) { ruangan ->
            ruangan?.let {
                textRuangan.text = "Ruangan: ${it.namaRuangan}"
            }
        }

        inventarisViewModel.getKaryawanById(barang.karyawanId).observe(viewLifecycleOwner) { karyawan ->
            karyawan?.let {
                textKaryawan.text = "Penanggung Jawab: ${it.namaKaryawan}"
                textJabatan.text = "Jabatan: ${it.jabatan}"
                textKontak.text = "Kontak: ${it.kontak}"
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Detail Barang")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.button_back).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showEditRuanganDialog(ruangan: Ruangan) {

    }

    private fun showDetailKaryawanDialog(karyawan: Karyawan) {

    }

    private fun showEditKaryawanDialog(karyawan: Karyawan) {

    }
}

