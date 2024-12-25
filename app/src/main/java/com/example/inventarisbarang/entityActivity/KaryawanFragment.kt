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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventarisbarang.Backend.InventarisViewModel
import com.example.inventarisbarang.BarangAdapter
import com.example.inventarisbarang.R
import com.example.inventarisbarang.databinding.BarangPageBinding
import com.example.inventarisbarang.databinding.KaryawanPageBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

class KaryawanFragment : Fragment() {
    private lateinit var binding: KaryawanPageBinding
    private lateinit var inventarisViewModel: InventarisViewModel
    private lateinit var karyawanAdapter: BarangAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KaryawanPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inventarisViewModel = ViewModelProvider(requireActivity()).get(InventarisViewModel::class.java)
        setupRecyclerView()

        binding.fabAddButton.setOnClickListener {
            showAddKaryawanDialog()
        }
    }

    private fun setupRecyclerView() {
        karyawanAdapter = BarangAdapter(
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

        binding.recyclerView.apply {
            adapter = karyawanAdapter
            inventarisViewModel.allKaryawan.observe(viewLifecycleOwner, { karyawans ->
                val itemList = mutableListOf<Any>()
                itemList.add("Daftar Karyawan")
                itemList.addAll(karyawans)
                karyawanAdapter.submitList(itemList)
            })
            layoutManager = LinearLayoutManager(context)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddKaryawanDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_karyawan, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_karyawan)
        val editJabatan = dialogView.findViewById<EditText>(R.id.edit_jabatan)
        val editKontak = dialogView.findViewById<EditText>(R.id.edit_kontak)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Karyawan")
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val karyawan = Karyawan(
                namaKaryawan = nama,
                jabatan = jabatan,
                kontak = kontak
            )

            inventarisViewModel.insertKaryawan(karyawan)
            Toast.makeText(requireContext(), "Karyawan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditKaryawanDialog(karyawan: Karyawan) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_karyawan, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_karyawan)
        val editJabatan = dialogView.findViewById<EditText>(R.id.edit_jabatan)
        val editKontak = dialogView.findViewById<EditText>(R.id.edit_kontak)

        // Set data karyawan ke EditText
        editNama.setText(karyawan.namaKaryawan)
        editJabatan.setText(karyawan.jabatan)
        editKontak.setText(karyawan.kontak)

        val dialog = AlertDialog.Builder(requireContext())
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedKaryawan = karyawan.copy(
                namaKaryawan = nama,
                jabatan = jabatan,
                kontak = kontak
            )

            inventarisViewModel.updateKaryawan(updatedKaryawan)
            Toast.makeText(requireContext(), "Karyawan updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showDetailKaryawanDialog(karyawan: Karyawan) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_detail_karyawan, null)

        val textNama = dialogView.findViewById<TextView>(R.id.textNama)
        val textJabatan = dialogView.findViewById<TextView>(R.id.textJabatan)
        val textKontak = dialogView.findViewById<TextView>(R.id.textKontak)

        textNama.text = "Nama Karyawan: ${karyawan.namaKaryawan}"
        textJabatan.text = "Jabatan: ${karyawan.jabatan}"
        textKontak.text = "Kontak: ${karyawan.kontak}"

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Detail Karyawan")
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

    private fun showEditBarangDialog(barang: Barang) {

    }

    private fun showDetailBarangDialog(barang: Barang) {

    }
}