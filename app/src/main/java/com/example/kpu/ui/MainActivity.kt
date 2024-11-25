package com.example.kpu.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.InvalidationTracker
import com.example.kpu.R
import com.example.kpu.database.KPU
import com.example.kpu.database.KPUDao
import com.example.kpu.database.KPURoomDatabase
import com.example.kpu.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var kpuDao: KPUDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize PrefManager
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        // Initialize Room Database
        val db = KPURoomDatabase.getDatabase(this)
        kpuDao = db!!.KPUDao()!!

        // Setup RecyclerView
        binding.dataKpu.layoutManager = LinearLayoutManager(this)

        // Observe LiveData for updates to the KPU list
        kpuDao.allNotes.observe(this, Observer { list ->
            val adapter = KPUAdapter(
                this,
                list,
                { kpu -> deleteData(kpu) },
                { kpu -> editData(kpu) },
                { kpu -> viewData(kpu) }
            )
            binding.dataKpu.adapter = adapter
        })

        // Button Add Data click listener
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        // Button Logout click listener
        binding.btnLogout.setOnClickListener {
            prefManager.setLoggedIn(false)
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    // Function to check if the user is logged in
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    // Function to delete a KPU
    private fun deleteData(kpu: KPU) {
        Thread {
            kpuDao.delete(kpu)
        }.start()
    }

    // Function to edit a KPU
    private fun editData(kpu: KPU) {
        val intent = Intent(this, EditDataActivity::class.java)
        intent.putExtra("kpu_id", kpu.id)
        startActivity(intent)
    }

    // Function to view a KPU
    private fun viewData(kpu: KPU) {
        val intent = Intent(this, ViewDataActivity::class.java)
        intent.putExtra("kpu_id", kpu.id)
        startActivity(intent)
    }
}
