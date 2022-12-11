package com.example.batoboapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Jasa::class], version = 1)
abstract class JasaRoomDatabase : RoomDatabase() {
    abstract fun jasaDao(): JasaDao

    companion object {
        @Volatile
        private var INSTANCE: JasaRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): JasaRoomDatabase {
            if (INSTANCE == null) {
                synchronized(JasaRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        JasaRoomDatabase::class.java, "jasa_database"
                    )
                        .build()
                }
            }
            return INSTANCE as JasaRoomDatabase
        }
    }
}