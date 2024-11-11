package com.example.inventarisbarang

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import com.example.inventarisbarang.viewmodel.InventarisViewModel

class BarangAdapter(
    private val onItemClickListener: (Barang) -> Unit,
    private val editClickListener: (Barang) -> Unit,
    private val karyawanEditClickListener: (Karyawan) -> Unit,
    private val karyawanItemClickListener: (Karyawan) -> Unit,
    private val ruanganEditClickListener: (Ruangan) -> Unit,
    private val viewModel: InventarisViewModel
) : ListAdapter<Any, RecyclerView.ViewHolder>(BarangDiffCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_BARANG = 1
        private const val TYPE_KARYAWAN = 2
        private const val TYPE_RUANGAN = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is String -> TYPE_HEADER  // Handle header type
            is Barang -> TYPE_BARANG // Handle Barang type
            is Karyawan -> TYPE_KARYAWAN // Handle Karyawan type
            is Ruangan -> TYPE_RUANGAN // Handle Ruangan type
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView: TextView = itemView.findViewById(R.id.textViewHeader)

        fun bind(header: String) {
            headerTextView.text = header
        }
    }

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.nama_text_view)
        val kategoriTextView: TextView = itemView.findViewById(R.id.kategori_text_view)
        val jumlahTextView: TextView = itemView.findViewById(R.id.jumlah_text_view)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)

        fun bind(barang: Barang) {
            namaTextView.text = barang.nama
            kategoriTextView.text = "Kategori: ${barang.kategori}"
            jumlahTextView.text = "Jumlah: ${barang.jumlah} Unit"

            itemView.setOnClickListener {
                onItemClickListener(barang)
            }
            buttonDelete.setOnClickListener {
                viewModel.deleteBarang(barang)
            }
            buttonEdit.setOnClickListener {
                editClickListener(barang)
            }
        }
    }

    inner class KaryawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.namaKaryawan)
        val jabatanTextView: TextView = itemView.findViewById(R.id.jabatan)
        val nomorTextView: TextView = itemView.findViewById(R.id.nomor)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)

        fun bind(karyawan: Karyawan) {
            namaTextView.text = karyawan.namaKaryawan
            jabatanTextView.text = "Jabatan: ${karyawan.jabatan}"
            nomorTextView.text = "Nomor: ${karyawan.kontak}"

            itemView.setOnClickListener {
                karyawanItemClickListener(karyawan)
            }
            buttonDelete.setOnClickListener {
                viewModel.deleteKaryawan(karyawan)
            }
            buttonEdit.setOnClickListener {
                karyawanEditClickListener(karyawan)
            }
        }
    }

    inner class ruanganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.namaRuangan)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)

        fun bind(ruangan: Ruangan) {
            namaTextView.text = ruangan.namaRuangan

            buttonDelete.setOnClickListener {
                viewModel.deleteRuangan(ruangan)
            }
            buttonEdit.setOnClickListener{
                ruanganEditClickListener(ruangan)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_BARANG -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.barang_item, parent, false)
                BarangViewHolder(view)
            }
            TYPE_KARYAWAN -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.karyawan_item, parent, false)
                KaryawanViewHolder(view)
            }
            TYPE_RUANGAN -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.ruangan_item, parent, false)
                ruanganViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as String)
            is BarangViewHolder -> holder.bind(item as Barang)
            is KaryawanViewHolder -> holder.bind(item as Karyawan)
            is ruanganViewHolder -> holder.bind(item as Ruangan)
        }
    }

    class BarangDiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is Barang && newItem is Barang) {
                oldItem.id == newItem.id
            } else if (oldItem is Karyawan && newItem is Karyawan) {
                oldItem.id == newItem.id
            } else if (oldItem is String && newItem is String) {
                oldItem == newItem
            } else {
                false
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }
    }
}
