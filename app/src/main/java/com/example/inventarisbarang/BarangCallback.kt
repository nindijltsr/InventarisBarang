package com.example.inventarisbarang

import androidx.recyclerview.widget.DiffUtil
import com.example.inventarisbarang.entity.Barang

class BarangCallback: DiffUtil.ItemCallback<Barang>() {
    override fun areItemsTheSame(oldItem: Barang, newItem: Barang): Boolean {
        // Bandingkan item berdasarkan ID
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Barang, newItem: Barang): Boolean {
        // Bandingkan isi item untuk memastikan mereka benar-benar sama
        return oldItem == newItem
    }

}