package com.example.tugas_pasienapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tugas_pasienapi.model.LoginRequest
import com.example.tugas_pasienapi.network.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // ID tidak berubah dari XML lama — semua masih sama
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        // Cek jika user sudah pernah login sebelumnya (punya token)
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val savedToken = prefs.getString("token", null)
        if (savedToken != null) {
            startActivity(Intent(this, PasienActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            prosesLogin()
        }
    }

    private fun prosesLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            btnLogin.isEnabled = false

            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.apiService.login(request)

                if (response.isSuccessful) {
                    val loginData = response.body()?.data
                    val token = loginData?.token.orEmpty()
                    val userName = loginData?.user?.name.orEmpty()

                    val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                    prefs.edit()
                        .putString("token", token)
                        .putString("user_name", userName)
                        .apply()

                    Toast.makeText(this@MainActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, PasienActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Email atau password salah!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Gagal koneksi ke server", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
                btnLogin.isEnabled = true
            }
        }
    }
}