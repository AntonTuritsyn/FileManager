package com.bignerdranch.android.filemanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileHashDao {
    @Query("SELECT * FROM files")
    fun getAll(): List<FileHash>

    @Query("SELECT * FROM files WHERE filename = :filename")
    fun findByFilename(filename: String): FileHash?

    @Insert
    fun insert(fileHash: FileHash)

    @Delete
    fun delete(fileHash: FileHash)
}