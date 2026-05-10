package com.example.tugas_pasienapi

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_pasienapi.adapter.PasienAdapter
import com.example.tugas_pasienapi.network.RetrofitClient
import kotlinx.coroutines.launch

class PasienActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var rvPasien: RecyclerView
    // Menggunakan layoutLoading (LinearLayout pembungkus) sebagai kontrol tampilan loading
    private lateinit var layoutLoading: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        // Ambil referensi view — ID rvPasien & tvUserName tetap sama
        tvUserName  = findViewById(R.id.tvUserName)
        rvPasien    = findViewById(R.id.rvPasien)
        layoutLoading = findViewById(R.id.layoutLoading) // ID baru dari XML

        rvPasien.layoutManager = LinearLayoutManager(this)

        // Ambil data dari SharedPreferences
        val prefs    = getSharedPreferences("auth", MODE_PRIVATE)
        val userName = prefs.getString("user_name", "Pengguna")
        val token    = prefs.getString("token", "")

        tvUserName.text = userName

        loadDataPasien(token!!)
    }

    private fun loadDataPasien(token: String) {
        lifecycleScope.launch {
            layoutLoading.visibility = View.VISIBLE // tampilkan card loading

            try {
                val response = RetrofitClient.apiService.getPasien("Bearer $token")

                if (response.isSuccessful) {
                    val listPasien = response.body()?.data ?: emptyList()

                    if (listPasien.isEmpty()) {
                        Toast.makeText(this@PasienActivity, "Belum ada data pasien", Toast.LENGTH_SHORT).show()
                    } else {
                        val adapter = PasienAdapter(listPasien)
                        rvPasien.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@PasienActivity, "Sesi habis atau gagal ambil data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PasienActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                layoutLoading.visibility = View.GONE // sembunyikan card loading
            }
        }
    }
}