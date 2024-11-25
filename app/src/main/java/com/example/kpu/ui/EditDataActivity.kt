package com.example.kpu.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kpu.database.KPU
import com.example.kpu.database.KPUDao
import com.example.kpu.database.KPURoomDatabase
import com.example.kpu.databinding.ActivityEditDataBinding

class EditDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDataBinding
    private lateinit var kpuDao: KPUDao
    private var kpuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room Database and DAO
        val db = KPURoomDatabase.getDatabase(this)
        kpuDao = db!!.KPUDao()!!

        // Get KPU ID from Intent
        kpuId = intent.getIntExtra("kpu_id", 0)

        // Load existing data based on the KPU ID
        loadKPUData(kpuId)

        // Set up the Save button listener to update the data
        binding.btnSave.setOnClickListener {
            val updatedKPU = KPU(
                id = kpuId,
                nama = binding.inputNamaPemilih.text.toString(),
                nik = binding.inputNik.text.toString(),
                gender = getSelectedGender(),
                alamat = binding.inputAlamat.text.toString()
            )
            updateKPUData(updatedKPU)
        }
    }

    private fun loadKPUData(kpuId: Int) {
        // Get the KPU object by its ID
        Thread {
            val kpu = kpuDao.getKPUById(kpuId)
            runOnUiThread {
                // Set the values of the fields with the existing KPU data
                binding.inputNamaPemilih.setText(kpu.nama)
                binding.inputNik.setText(kpu.nik)
                binding.inputAlamat.setText(kpu.alamat)

                // Set the gender radio button selection based on existing gender
                if (kpu.gender == "Laki-Laki") {
                    binding.genderLaki.isChecked = true
                } else {
                    binding.genderPerempuan.isChecked = true
                }
            }
        }.start()
    }

    private fun getSelectedGender(): String {
        // Get selected gender from the RadioGroup
        return when {
            binding.genderLaki.isChecked -> "Laki-Laki"
            binding.genderPerempuan.isChecked -> "Perempuan"
            else -> ""
        }
    }

    private fun updateKPUData(kpu: KPU) {
        // Update the KPU data in the database
        Thread {
            kpuDao.update(kpu)
            runOnUiThread {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                finish() // Close the Edit Activity and return to MainActivity
            }
        }.start()
    }
}
