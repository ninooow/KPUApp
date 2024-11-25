package com.example.kpu.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu.R
import com.example.kpu.databinding.ActivityRegistrasiBinding

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrasiBinding
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        with(binding) {
            btnRegistrasi.setOnClickListener {
                val username = inputUsn.text.toString()
                val password = inputPsw.text.toString()
                val confirmPassword = inputPswKonfirmasi.text.toString()
                if (username.isEmpty() || password.isEmpty() ||
                    confirmPassword.isEmpty()) {
                    Toast.makeText(
                        this@RegistrasiActivity,
                        "Mohon isi semua data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != confirmPassword) {
                    Toast.makeText(this@RegistrasiActivity, "Password tidak sama",
                        Toast.LENGTH_SHORT)
                        .show()
                } else {
                    prefManager.saveUsername(username)
                    prefManager.savePassword(password)
                    prefManager.setLoggedIn(true)
                    checkLoginStatus()
                }
            }
            txtLogin.setOnClickListener {
                startActivity(Intent(this@RegistrasiActivity,
                    LoginActivity::class.java))
            }
        }
    }
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (isLoggedIn) {
            Toast.makeText(this@RegistrasiActivity, "Registrasi berhasil",
                Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegistrasiActivity, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this@RegistrasiActivity, "Registrasi gagal",
                Toast.LENGTH_SHORT).show()
        }
    }
}