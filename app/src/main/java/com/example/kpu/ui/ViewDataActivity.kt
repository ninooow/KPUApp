package com.example.kpu.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kpu.database.KPU
import com.example.kpu.database.KPUDao
import com.example.kpu.database.KPURoomDatabase
import com.example.kpu.databinding.ActivityViewDataBinding

class ViewDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewDataBinding
    private lateinit var kpuDao: KPUDao
    private var kpuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityViewDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room Database and DAO
        val db = KPURoomDatabase.getDatabase(this)
        kpuDao = db!!.KPUDao()!!

        // Get KPU ID from Intent
        kpuId = intent.getIntExtra("kpu_id", 0)

        // Load KPU data based on the KPU ID
        loadKPUData(kpuId)

        // Handle the back button click
        binding.btnBack.setOnClickListener {
            finish() // Finish the activity and go back to previous screen
        }
    }

    private fun loadKPUData(kpuId: Int) {
        // Get the KPU object by its ID
        Thread {
            val kpu = kpuDao.getKPUById(kpuId)
            runOnUiThread {
                // Set the values of the TextViews with the existing KPU data
                binding.namaPemilih.text = kpu.nama
                binding.nik.text = kpu.nik
                binding.gender.text = kpu.gender
                binding.alamat.text = kpu.alamat
            }
        }.start()
    }
}
