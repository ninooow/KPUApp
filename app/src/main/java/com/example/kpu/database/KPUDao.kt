package com.example.kpu.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface KPUDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(kpu: KPU)
    @Update
    fun update(kpu: KPU)
    @Delete
    fun delete(kpu: KPU)
    @Query("SELECT * FROM pemilih_table WHERE id = :id LIMIT 1")
    fun getKPUById(id: Int): KPU
    @get:Query("SELECT * from pemilih_table ORDER BY id ASC")
    val allNotes: LiveData<List<KPU>>
}