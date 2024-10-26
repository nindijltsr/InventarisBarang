package com.example.inventarisbarang

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventarisbarang.dao.BarangDao
import com.example.inventarisbarang.dao.KaryawanDao
import com.example.inventarisbarang.dao.RuanganDao
import com.example.inventarisbarang.database.InventarisDatabase
import com.example.inventarisbarang.entity.Barang
import com.example.inventarisbarang.entity.Karyawan
import com.example.inventarisbarang.entity.Ruangan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var BarangDao : BarangDao
    private lateinit var KaryawanDao: KaryawanDao
    private lateinit var RuanganDao: RuanganDao
    private lateinit var db: InventarisDatabase

    private val barang = Barang(1, "Laptop", "Elektronik", 10, "20 September 2024", "Baik", 1234, 12345)
    private val karyawan = Karyawan(2, "Sela", "Sekretaris", "08132379")
    private val ruangan = Ruangan(3, "101")

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, InventarisDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        BarangDao = db.barangDao()
        KaryawanDao = db.karyawanDao()
        RuanganDao = db.ruanganDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveBarang(){
        BarangDao.insert(barang)
        val result = BarangDao.getAll()
        assert(result.size == 1)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveKaryawan(){
        KaryawanDao.insert(karyawan)
        val result = KaryawanDao.getAll()
        assert(result.size == 1)
    }

}