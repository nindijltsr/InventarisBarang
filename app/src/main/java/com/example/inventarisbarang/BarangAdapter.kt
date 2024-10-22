package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class BarangAdapter(
    private val onItemClickListener: (Barang) -> Unit,
    private val editClickListener: (Barang) -> Unit,
    private val viewModel: InventarisViewModel // Tambahkan parameter kedua untuk ViewModel
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {
    private var barangList = emptyList<Barang>()

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view)
        val kategoriTextView: TextView = itemView.findViewById(R.id.kategori_text_view)
        val jumlahTextView: TextView = itemView.findViewById(R.id.jumlah_text_view)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return BarangViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val currentBarang = barangList[position]
        holder.namaTextView.text = currentBarang.nama
        holder.kategoriTextView.text = "Kategori: ${currentBarang.kategori}"
        holder.jumlahTextView.text = "Jumlah: ${currentBarang.jumlah}"

        holder.itemView.setOnClickListener {
            onItemClickListener(currentBarang)
        }
        holder.buttonDelete.setOnClickListener {
            viewModel.deleteBarang(currentBarang) // Menggunakan ViewModel untuk delete
        }
        holder.buttonEdit.setOnClickListener {
            editClickListener(currentBarang) // Panggil callback untuk menampilkan dialog
        }
    }

    override fun getItemCount() = barangList.size

    @SuppressLint("NotifyDataSetChanged")
    internal fun setBarang(barangs: List<Barang>) {
        this.barangList = barangs
        notifyDataSetChanged()
    }
}
