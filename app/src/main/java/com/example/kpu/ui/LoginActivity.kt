package com.example.kpu.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kpu.R
import com.example.kpu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        with(binding){
            btnLogin.setOnClickListener {
                val username = inputUsn.text.toString()
                val password = inputPsw.text.toString()
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Mohon isi semua data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isValidUsernamePassword()) {
                        prefManager.setLoggedIn(true)
                        checkLoginStatus()
                    } else {
                        Toast.makeText(this@LoginActivity, "Username atau assword salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrasiActivity::class.java))
            }
        }
    }
    private fun isValidUsernamePassword(): Boolean{
        val username = prefManager.getUsername()
        val password = prefManager.getPassword()
        val inputUsername = binding.inputUsn.text.toString()
        val inputPassword = binding.inputPsw.text.toString()
        return username == inputUsername && password == inputPassword
    }
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (isLoggedIn) {
            Toast.makeText(this@LoginActivity, "Login berhasil",
                Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this@LoginActivity,
                    MainActivity::class.java)
            )
            finish()
        } else {
            Toast.makeText(this@LoginActivity, "Login gagal",
                Toast.LENGTH_SHORT).show()
        }
    }
}