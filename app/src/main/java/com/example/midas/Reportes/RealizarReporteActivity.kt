package com.example.midas.Reportes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.MenuActivity
import com.example.midas.R

class RealizarReporteActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_realizar_reporte)
        val btnRealizarReporte = findViewById<ImageButton>(R.id.btnRealizarReporte)
        val btnVerReporte = findViewById<ImageButton>(R.id.btnVerReporte)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)

        btnRealizarReporte.setOnClickListener() {
            val intent = Intent(this, LlenarReporteActivity::class.java)
            startActivity(intent)
        }

        btnVerReporte.setOnClickListener() {
           /* val intent = Intent(this, RealizarReporteActivity::class.java)
            startActivity(intent)*/
        }

        btnAtras.setOnClickListener() {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}