package com.example.inventarisbarang

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Pastikan ini mengarah ke layout yang benar

        // Inisialisasi tombol
        buttonNext = findViewById(R.id.button_next)

        // Set listener untuk tombol Next
        buttonNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: menutup HomeActivity agar tidak bisa kembali ke halaman ini
        }
    }
}
