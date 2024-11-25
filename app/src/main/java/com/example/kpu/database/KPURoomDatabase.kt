package com.example.kpu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [KPU::class], version = 1, exportSchema = false)
abstract class KPURoomDatabase : RoomDatabase() {
    abstract fun KPUDao(): KPUDao?
    companion object{
        @Volatile
        private var INSTANCE : KPURoomDatabase? = null
        fun getDatabase(context: Context): KPURoomDatabase?{
            if (INSTANCE == null) {
                synchronized(KPURoomDatabase::class.java) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        KPURoomDatabase::class.java, "pemilih_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}