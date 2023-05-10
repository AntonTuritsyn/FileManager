package com.bignerdranch.android.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.pognali_button)

        button.setOnClickListener {
            if (checkPermission()) {
                val intent = Intent(this,ListActivity::class.java)
                val path = Environment.getExternalStorageDirectory().path
                intent.putExtra("path", path)
                startActivity(intent)
            } else {
                requestPermission()
            }
        }
    }
    // проверка, было ли дано разрешение
    private fun checkPermission(): Boolean {
        return when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) -> true
            else -> false
        }
    }
    // запрос на получение разрешения на чтение файлов с SD
    private fun requestPermission(){
            val isGranted: Boolean = ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (isGranted) {
                    Toast.makeText(this, "Проверка", Toast.LENGTH_LONG).show()
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)

                }
            }
    }
