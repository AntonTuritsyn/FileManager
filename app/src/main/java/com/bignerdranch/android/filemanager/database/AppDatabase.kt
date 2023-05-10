package com.bignerdranch.android.filemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FileHash::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun fileHashDao(): FileHashDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "filename-db"
                ).build()
            }
            return instance as AppDatabase
        }
    }
}