package com.example.inventarisbarang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

@Database(entities = [Barang::class, Karyawan::class, Ruangan::class], version = 2)
abstract class InventarisDatabase : RoomDatabase() {
    abstract fun barangDao(): BarangDao
    abstract fun karyawanDao(): KaryawanDao
    abstract fun ruanganDao(): RuanganDao

    companion object {
        @Volatile
        private var INSTANCE: InventarisDatabase? = null

        fun getDatabase(context: Context): InventarisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventarisDatabase::class.java,
                    "inventaris_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
