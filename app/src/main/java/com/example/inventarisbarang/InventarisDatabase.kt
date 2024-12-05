package com.example.inventarisbarang.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan

// Perbarui versi dari 1 ke 2
@Database(entities = [Barang::class, Karyawan::class, Ruangan::class], version = 2, exportSchema = false)
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
                    "inventaris-database"
                )
                    // Tambahkan migrasi dari versi 1 ke 2
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Definisikan migrasi dari versi 1 ke 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Misalnya, menambahkan kolom baru 'new_column' pada tabel 'barang'
                database.execSQL("ALTER TABLE barang ADD COLUMN new_column TEXT")
            }
        }
    }
}
