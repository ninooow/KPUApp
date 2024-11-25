package com.example.kpu.ui

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kpu.database.KPU
import com.example.kpu.database.KPUDao
import com.example.kpu.database.KPURoomDatabase
import com.example.kpu.databinding.ActivityAddDataBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding
    private lateinit var KPUDao: KPUDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = KPURoomDatabase.getDatabase(this)
        KPUDao = db?.KPUDao()!!
        executorService = Executors.newSingleThreadExecutor()

        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val nama = binding.inputNamaPemilih.text.toString()
        val nik = binding.inputNik.text.toString()
        val gender = getSelectedGender()
        val alamat = binding.inputAlamat.text.toString()

        if (nama.isEmpty() || nik.isEmpty() || gender.isEmpty() || alamat.isEmpty()) {
            showMessage("Semua kolom harus diisi!")
            return
        }

        val kpu = KPU(
            nama = nama,
            nik = nik,
            gender = gender,
            alamat = alamat
        )

        executorService.execute {
            KPUDao.insert(kpu)
            runOnUiThread {
                showMessage("Data berhasil disimpan!")
                finish()
            }
        }
    }

    private fun getSelectedGender(): String {
        val selectedGenderId = binding.radioButtonGender.checkedRadioButtonId
        val selectedGenderButton: RadioButton = findViewById(selectedGenderId)
        return selectedGenderButton.text.toString()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
