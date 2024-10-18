package com.example.inventarisbarang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Barang

class BarangAdapter(private val onItemClickListener: (Barang) -> Unit) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    private var barangList = emptyList<Barang>()

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view)
        val kategoriTextView: TextView = itemView.findViewById(R.id.kategori_text_view)
        val jumlahTextView: TextView = itemView.findViewById(R.id.jumlah_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return BarangViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val currentBarang = barangList[position]
        holder.namaTextView.text = currentBarang.nama
        holder.kategoriTextView.text = currentBarang.kategori
        holder.jumlahTextView.text = currentBarang.jumlah.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener(currentBarang)
        }
    }

    override fun getItemCount() = barangList.size

    internal fun setBarang(barangs: List<Barang>) {
        this.barangList = barangs
        notifyDataSetChanged()
    }
}