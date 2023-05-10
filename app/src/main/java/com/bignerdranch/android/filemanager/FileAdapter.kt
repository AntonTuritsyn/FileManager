package com.bignerdranch.android.filemanager


/*
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.Date

class FileAdapter3(private var files: List<File>): RecyclerView.Adapter<FileListFragment.FileAdapter.FileViewHolder>() {

    class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.file_name)
        val size: TextView = itemView.findViewById(R.id.file_size)
        val date: TextView = itemView.findViewById(R.id.file_date)
        val icon: ImageView = itemView.findViewById(R.id.file_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_file, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]

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
    }

    override fun getItemCount(): Int {
        return files.size
    }

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

    private fun getFileExtension(file: File): String {
        val name = file.name
        val lastDotIndex = name.lastIndexOf('.')
        return if (lastDotIndex == -1) "" else name.substring(lastDotIndex + 1).lowercase()
    }

    private fun getIconForExtension(extension: String): Int {
        // возвращаем иконку в зависимости от расширения файла
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

    private fun openDirectory(directory: File) {
        files = directory.listFiles().toList()
        notifyDataSetChanged()
    }
}

*/
/*val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(directory.absolutePath), "resource/folder")
        startActivity(intent)*//*
*/
/*

        *//*

*/
/*val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(directory.absolutePath), "resource/folder")
        startActivity(intent)*//*
*/
/*

    }

    *//*

*/
/*private fun openFile(file: File, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        intent.setDataAndType(uri, getMimeType(file.absolutePath))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(Intent.createChooser(intent, "Open file with..."))
    }

    private fun getMimeType(url: String): String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }*//*
*/
/*


    *//*

*/
/*private fun openFile(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
        intent.setDataAndType(uri, getMimeType(file))
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(intent, "Open file with..."))
    }

    private fun getMimeType(file: File): String {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extension = file.extension
        return mimeTypeMap.getMimeTypeFromExtension(extension) ?: "application/octet-stream"
    }*//*
*/
/*

    private fun updateFiles(newFiles: List<File>) {
        apply {
            files.clear()
            files.addAll(newFiles)
            notifyDataSetChanged()
        }

    }
}*/
