package com.bignerdranch.android.filemanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class FileHash (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo(name = "filename")
    var filename: String,
    @ColumnInfo(name = "hash")
    var hash: String
)