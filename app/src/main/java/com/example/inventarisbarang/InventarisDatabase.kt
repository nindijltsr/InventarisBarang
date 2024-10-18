package com.example.inventarisbarang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.entity.Barang

@Database(entities = [Barang::class], version = 1)
abstract class InventarisDatabase : RoomDatabase() {
    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: InventarisDatabase? = null

        fun getDatabase(context: Context): InventarisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventarisDatabase::class.java,
                    "inventaris_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
