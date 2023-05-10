package com.bignerdranch.android.filemanager.database

import android.content.Context
import java.io.File

class FileHelper(private val context: Context) {

    private val db = DBHelper(context)

    fun getModifiedFiles(directory: File): List<File> {
        val allFiles = getFilesInDirectory(directory)

        return allFiles.filter { file ->
            val savedHashCode = db.getFileHashCode(file.absolutePath)
            savedHashCode == null || savedHashCode != file.hashCode()
        }
    }

    private fun getFilesInDirectory(directory: File): List<File> {
        val files = mutableListOf<File>()

        directory.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                files.addAll(getFilesInDirectory(file))
            } else {
                files.add(file)
            }
        }

        return files
    }

}