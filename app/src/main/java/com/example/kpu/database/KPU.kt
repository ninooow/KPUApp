package com.example.kpu.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pemilih_table")
data class KPU(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int=0,
    @ColumnInfo(name="nama")
    val nama: String,
    @ColumnInfo(name="nik")
    val nik: String,
    @ColumnInfo(name="gender")
    val gender: String,
    @ColumnInfo(name="alamat")
    val alamat: String
)
