package com.example.inventarisbarang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class KaryawanAdapter(
    private val onItemClickListener: (Karyawan) -> Unit,
    private val viewModel: InventarisViewModel // Tambahkan ViewModel
) : RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder>() {

    private var karyawanList = emptyList<Karyawan>()

    inner class KaryawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view)
        val jabatanTextView: TextView = itemView.findViewById(R.id.posisi_text_view)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
        return KaryawanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        val currentKaryawan = karyawanList[position]
        holder.namaTextView.text = currentKaryawan.namaKaryawan
        holder.jabatanTextView.text = currentKaryawan.jabatan

        holder.itemView.setOnClickListener {
            onItemClickListener(currentKaryawan)
        }

        holder.buttonDelete.setOnClickListener {
            viewModel.deleteKaryawan(currentKaryawan) // Menghapus karyawan menggunakan ViewModel
        }

        holder.buttonEdit.setOnClickListener {
            onItemClickListener(currentKaryawan)
        }
    }

    override fun getItemCount() = karyawanList.size

    internal fun setKaryawan(karyawans: List<Karyawan>) {
        this.karyawanList = karyawans
        notifyDataSetChanged()
    }
}
