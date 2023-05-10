package com.bignerdranch.android.filemanager

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.Formatter.formatFileSize
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.Date

class FileListFragment: Fragment() {
    private lateinit var fileRecyclerView: RecyclerView
    private var adapter: FileAdapter? = null

    private var files = mutableListOf<File>()

    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_file_list, container, false)

        fileRecyclerView = view.findViewById(R.id.file_recycler_view) as RecyclerView
        fileRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }
    // подключение Adapter к RecyclerView
    private fun updateUI() {
        val files = files
        adapter = FileAdapter(files)
        fileRecyclerView.adapter = adapter
    }

    inner class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val name: TextView = itemView.findViewById(R.id.file_name)
        val size: TextView = itemView.findViewById(R.id.file_size)
        val date: TextView = itemView.findViewById(R.id.file_date)
        val icon: ImageView = itemView.findViewById(R.id.file_icon)

    }

    inner class FileAdapter(var file: List<File>) : RecyclerView.Adapter<FileViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_file, parent, false)
            return FileViewHolder(view)
        }

        override fun getItemCount() = file.size

        override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
            val file = file[position]

            holder.name.text = file.name
            holder.size.text = formatFileSize(file.length())
            holder.date.text = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date(file.lastModified()))

            if (file.isDirectory) {
                holder.icon.setImageResource(R.drawable.ic_folder)
            } else {
                val extension = getFileExtension(file)
                val icon = getIconForExtension(extension)
                holder.icon.setImageResource(icon)
            }

            holder.itemView.setOnClickListener {
                if (file.isDirectory) {
                    // открыть папку
                    openDirectory(file)
                } else {
                    // открыть файл
                    openFile(file)
                }
            }
            // на долгое нажатие устанавливается возможность делиться файлом
            holder.itemView.setOnLongClickListener {
                shareFile(file, context)
            }
        }
        // открытие папки
        private fun openDirectory(directory: File) {
            val f = directory.listFiles()
            if (f != null) {
                file = f.toList()
            } else {

            }
            notifyDataSetChanged()
        }
        // открытие файла
        private fun openFile(file: File) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            intent.setDataAndType(uri, getMimeType(file))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(Intent.createChooser(intent, "Open file with..."))
        }
        // реализация возможности поделиться файлом
        private fun shareFile(file: File, context: Context): Boolean {
            return if (file.isDirectory) {
                false
            } else {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${requireContext().packageName}.provider",
                    file
                )
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "application/pdf"
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(Intent.createChooser(shareIntent, "Share File"))
                true
            }
        }

        private fun getMimeType(file: File): String {
            val mimeTypeMap = MimeTypeMap.getSingleton()
            val extension = file.extension
            return mimeTypeMap.getMimeTypeFromExtension(extension) ?: "application/octet-stream"
        }
        // возвращает размер файла в удобном формате
        private fun formatFileSize(size: Long): String {
            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            var fileSize = size.toDouble()
            var unitIndex = 0
            while (fileSize >= 1024 && unitIndex < units.size - 1) {
                fileSize /= 1024
                unitIndex++
            }
            return String.format("%.1f %s", fileSize, units[unitIndex])
        }
        // возвращает расширение файла
        private fun getFileExtension(file: File): String {
            val name = file.name
            val lastDotIndex = name.lastIndexOf('.')
            return if (lastDotIndex == -1) "" else name.substring(lastDotIndex + 1).lowercase()
        }
        // возвращает иконку в зависимости от расширения файла
        private fun getIconForExtension(extension: String): Int {
            when (extension) {
                "jpg", "jpeg", "png", "gif" -> return R.drawable.ic_image
                "mp3", "wav", "flac" -> return R.drawable.ic_music
                "mp4", "avi", "mkv" -> return R.drawable.ic_video
                "doc", "docx" -> return R.drawable.ic_doc
                "xls", "xlsx" -> return R.drawable.ic_xls
                "pdf" -> return R.drawable.ic_pdf
                else -> return R.drawable.ic_file
            }
        }
    }
}