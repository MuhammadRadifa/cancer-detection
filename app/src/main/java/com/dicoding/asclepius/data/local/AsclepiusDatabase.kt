package com.dicoding.asclepius.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class AsclepiusDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    companion object{
        @Volatile
        private var INSTANCE: AsclepiusDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context):AsclepiusDatabase{
            if (INSTANCE == null){
                synchronized(AsclepiusDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AsclepiusDatabase::class.java, "asclepius_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as AsclepiusDatabase
        }
    }
}