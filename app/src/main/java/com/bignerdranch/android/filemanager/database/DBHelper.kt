package com.bignerdranch.android.filemanager.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "FileHashes.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "FileHashes"
        const val COLUMN_FILE_PATH = "FilePath"
        const val COLUMN_HASH_CODE = "HashCode"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_FILE_PATH TEXT PRIMARY KEY, $COLUMN_HASH_CODE INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getFileHashCode(filePath: String): Int? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_HASH_CODE FROM $TABLE_NAME WHERE $COLUMN_FILE_PATH = ?", arrayOf(filePath))

        return if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndex(COLUMN_HASH_CODE))
        } else {
            null
        }
    }

    fun updateFileHashCode(filePath: String, hashCode: Int) {
        val db = writableDatabase

        db.beginTransaction()
        try {
            val values = ContentValues().apply {
                put(COLUMN_FILE_PATH, filePath)
                put(COLUMN_HASH_CODE, hashCode)
            }

            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}