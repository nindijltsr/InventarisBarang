package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

// Deklarasi kelas BarangAdapter sebagai Adapter untuk RecyclerView
class BarangAdapter(
    private val onItemClickListener: (Barang) -> Unit, // Callback untuk item click
    private val editClickListener: (Barang) -> Unit, // Callback untuk tombol edit
    private val viewModel: InventarisViewModel // Parameter ViewModel untuk mengelola data
) : ListAdapter<Barang, BarangAdapter.BarangViewHolder>(BarangCallback()) { // Gunakan ListAdapter dengan DiffCallback

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
        val currentBarang = getItem(position) // Gunakan getItem dari ListAdapter
        holder.namaTextView.text = currentBarang.nama
        holder.kategoriTextView.text = "Kategori: ${currentBarang.kategori}"
        holder.jumlahTextView.text = "Jumlah: ${currentBarang.jumlah} Unit"
        holder.itemView.setOnClickListener {
            onItemClickListener(currentBarang)
        }
        holder.buttonDelete.setOnClickListener {
            viewModel.deleteBarang(currentBarang)
        }
        holder.buttonEdit.setOnClickListener {
            editClickListener(currentBarang)
        }
    }

    // Fungsi untuk memperbarui data barang di RecyclerView
    internal fun setBarang(barangs: List<Barang>) {
        submitList(barangs) // Gunakan submitList dari ListAdapter untuk memperbarui data
    }
}
