package com.example.inventarisbarang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class RuanganAdapter(
    private val onItemClickListener: (Ruangan) -> Unit,
    private val viewModel: InventarisViewModel // Tambahkan ViewModel
) : RecyclerView.Adapter<RuanganAdapter.RuanganViewHolder>() {

    private var ruanganList = emptyList<Ruangan>()

    inner class RuanganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuanganViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return RuanganViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RuanganViewHolder, position: Int) {
        val currentRuangan = ruanganList[position]
        holder.namaTextView.text = currentRuangan.namaRuangan

        holder.itemView.setOnClickListener {
            onItemClickListener(currentRuangan)
        }

        holder.buttonDelete.setOnClickListener {
            viewModel.deleteRuangan(currentRuangan) // Menghapus ruangan menggunakan ViewModel
        }
    }

    override fun getItemCount() = ruanganList.size

    internal fun setRuangan(ruangans: List<Ruangan>) {
        this.ruanganList = ruangans
        notifyDataSetChanged()
    }
}
