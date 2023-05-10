package com.bignerdranch.android.filemanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.filemanager.database.AppDatabase
import com.bignerdranch.android.filemanager.database.DBHelper
import java.io.File

class ListActivity : AppCompatActivity() {

    private val files = mutableListOf<File>()
    private lateinit var currentDirectory: File
    private lateinit var fileAdapter: FileListFragment.FileAdapter
    private lateinit var db: AppDatabase

    enum class SortField {
        NAME, SIZE, DATE, EXTENSION
    }

    enum class SortDirection {
        ASC, DESC
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var noFilesText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        // добавление тулбара для меню
        setSupportActionBar(findViewById(R.id.my_toolbar))

        recyclerView = findViewById(R.id.file_recycler_view)
        noFilesText = findViewById(R.id.nofiles_text)

        currentDirectory = Environment.getExternalStorageDirectory()
        fileAdapter = FileListFragment().FileAdapter(getFilesInDirectory(currentDirectory))

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = fileAdapter

        db = AppDatabase.getInstance(applicationContext)
    }

    // настройка меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // добавление пунктов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sortDirection: SortDirection
        val sortField: SortField
        return when (item.itemId) {
            R.id.sort_name_asc -> {
                sortField = SortField.NAME
                sortDirection = SortDirection.ASC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_name_desc -> {
                sortField = SortField.NAME
                sortDirection = SortDirection.DESC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_size_asc -> {
                sortField = SortField.SIZE
                sortDirection = SortDirection.ASC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_size_desc -> {
                sortField = SortField.SIZE
                sortDirection = SortDirection.DESC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_date_asc -> {
                sortField = SortField.DATE
                sortDirection = SortDirection.ASC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_date_desc -> {
                sortField = SortField.DATE
                sortDirection = SortDirection.DESC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_ext_asc -> {
                sortField = SortField.EXTENSION
                sortDirection = SortDirection.ASC
                update(files, sortField, sortDirection)
                true
            }

            R.id.sort_ext_desc -> {
                sortField = SortField.EXTENSION
                sortDirection = SortDirection.DESC
                update(files, sortField, sortDirection)
                true
            }

            R.id.all_files -> {
//                loadFiles(currentDirectory)
                getFilesInDirectory(currentDirectory)
                true
            }

            R.id.changed_files -> {
                getModifiedFiles(this, currentDirectory)
//                showChangedFiles()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    // получение файлов в папке
    private fun getFilesInDirectory(directory: File): List<File> {
        val files = mutableListOf<File>()
        directory.listFiles()?.forEach { file ->
            if (file.isFile || file.isDirectory) {
                files.add(file)
            }
        }
        return files.sortedBy { it.name }
    }
    // обновление списка файлов после сортировок
    private fun update(
        files: List<File>,
        sortField: SortField = SortField.NAME,
        sortDirection: SortDirection = SortDirection.ASC
    ) {
        val sortedFiles = sortFiles(files, sortField, sortDirection)
        this.files.clear()
        this.files.addAll(sortedFiles)
        (recyclerView.adapter as FileListFragment.FileAdapter).notifyDataSetChanged()
    }

    //TODO ЭТО ДЛЯ СОРТИРОВКИ
    private fun sortFiles(
        files: List<File>,
        sortField: SortField,
        sortDirection: SortDirection
    ): List<File> {
        return when (sortField) {
            SortField.NAME -> files.sortedWith(
                compareBy(
                    { it.isDirectory },
                    { it.name.lowercase() })
            )
            SortField.SIZE -> files.sortedWith(compareBy({ it.isDirectory }, { it.length() }))
            SortField.DATE -> files.sortedWith(compareBy({ it.isDirectory }, { it.lastModified() }))
            SortField.EXTENSION -> files.sortedWith(compareBy({ it.isDirectory }, { it.extension }))
        }.let {
            if (sortDirection == SortDirection.DESC) it.reversed() else it
        }
    }
    // получение только измененных файлов
    private fun getModifiedFiles(context: Context, directory: File): List<File> {
        val db = DBHelper(context)
        val allFiles = getFilesInDirectory(directory)
        return allFiles.filter { file ->
            val savedHashCode = db.getFileHashCode(file.absolutePath)
            savedHashCode == null || savedHashCode != file.hashCode()
        }
    }
}
