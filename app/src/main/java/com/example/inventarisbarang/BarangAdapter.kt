package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.viewmodel.InventarisViewModel

// Deklarasi kelas BarangAdapter sebagai Adapter untuk RecyclerView
class BarangAdapter(
    private val onItemClickListener: (Barang) -> Unit, // Callback untuk item click
    private val editClickListener: (Barang) -> Unit, // Callback untuk tombol edit
//    private val deleteClickListener: (Barang) -> Unit, // Callback untuk tombol delete
    private val viewModel: InventarisViewModel // Parameter ViewModel untuk mengelola data
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() { // Menurunkan dari RecyclerView.Adapter
    private var barangList = emptyList<Barang>() // Inisialisasi daftar barang kosong

    // Inner class ViewHolder untuk mengelola tampilan item di RecyclerView
    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view) // TextView untuk nama barang
        val kategoriTextView: TextView = itemView.findViewById(R.id.kategori_text_view) // TextView untuk kategori barang
        val jumlahTextView: TextView = itemView.findViewById(R.id.jumlah_text_view) // TextView untuk jumlah barang
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete) // Tombol untuk menghapus barang
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit) // Tombol untuk mengedit barang
    }

    // Fungsi untuk membuat ViewHolder baru saat RecyclerView membutuhkan item baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        // Inflate layout XML ke dalam View dan menggunakannya untuk ViewHolder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return BarangViewHolder(itemView) // Mengembalikan instance ViewHolder baru
    }

    // Fungsi untuk mengikat data barang ke ViewHolder
    @SuppressLint("SetTextI18n") // Menonaktifkan peringatan untuk penggunaan hard-coded text
    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val currentBarang = barangList[position] // Mendapatkan barang berdasarkan posisi
        holder.namaTextView.text = currentBarang.nama // Mengatur nama barang pada TextView
        holder.kategoriTextView.text = "Kategori: ${currentBarang.kategori}" // Mengatur kategori barang
        holder.jumlahTextView.text = "Jumlah: ${currentBarang.jumlah} Unit" // Mengatur jumlah barang

        holder.itemView.setOnClickListener {
            onItemClickListener(currentBarang) // Callback untuk item click
        }
        holder.buttonDelete.setOnClickListener {
            viewModel.deleteBarang(currentBarang) // Menghapus barang melalui ViewModel
        }
        holder.buttonEdit.setOnClickListener {
            editClickListener(currentBarang) // Callback untuk tombol edit
        }
    }

    // Mengembalikan jumlah item dalam barangList
    override fun getItemCount() = barangList.size

    // Fungsi untuk memperbarui data barang di RecyclerView
    @SuppressLint("NotifyDataSetChanged") // Menonaktifkan peringatan karena menggunakan notifyDataSetChanged()
    internal fun setBarang(barangs: List<Barang>) {
        this.barangList = barangs // Mengatur daftar barang yang baru
        notifyDataSetChanged() // Memberitahu RecyclerView bahwa data berubah
    }
}
