package com.example.inventarisbarang.entityActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventarisbarang.Backend.InventarisViewModel
import com.example.inventarisbarang.BarangAdapter
import com.example.inventarisbarang.R
import com.example.inventarisbarang.databinding.RuanganPageBinding
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

class RuanganFragment : Fragment() {
    private lateinit var binding: RuanganPageBinding
    private lateinit var inventarisViewModel: InventarisViewModel
    private lateinit var ruanganAdapter: BarangAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RuanganPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inventarisViewModel = ViewModelProvider(requireActivity()).get(InventarisViewModel::class.java)
        setupRecyclerView()

        binding.fabAddButton.setOnClickListener {
            showAddRuanganDialog()
        }
    }

    private fun setupRecyclerView() {
        ruanganAdapter = BarangAdapter(
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
            adapter = ruanganAdapter
            inventarisViewModel.allRuangan.observe(viewLifecycleOwner, { ruangans ->
                val itemList = mutableListOf<Any>()
                itemList.add("Daftar Ruangan")
                itemList.addAll(ruangans)
                ruanganAdapter.submitList(itemList)
            })
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showEditBarangDialog(barang: Barang) {

    }

    private fun showEditKaryawanDialog(karyawan: Karyawan) {

    }

    private fun showDetailKaryawanDialog(karyawan: Karyawan) {

    }

    private fun showDetailBarangDialog(barang: Barang) {

    }

    @SuppressLint("MissingInflatedId")
    private fun showAddRuanganDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_add_ruangan, null)

        val dialog = AlertDialog.Builder(requireContext())
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ruangan = Ruangan(namaRuangan = namaRuangan)
            inventarisViewModel.insertRuangan(ruangan)
            Toast.makeText(requireContext(), "Ruangan added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showEditRuanganDialog(ruangan: Ruangan) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_edit_ruangan, null)

        val editNama = dialogView.findViewById<EditText>(R.id.edit_nama_ruangan)

        // Set data ruangan ke EditText
        editNama.setText(ruangan.namaRuangan)

        val dialog = AlertDialog.Builder(requireContext())
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
                Toast.makeText(requireContext(), "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedRuangan = ruangan.copy(
                namaRuangan = nama
            )

            inventarisViewModel.updateRuangan(updatedRuangan)
            Toast.makeText(requireContext(), "Ruangan updated!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }
}