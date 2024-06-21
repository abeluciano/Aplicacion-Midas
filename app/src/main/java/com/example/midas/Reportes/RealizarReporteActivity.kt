/**
 * Actividad para realizar acciones relacionadas con reportes en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite al usuario realizar diversas acciones relacionadas con reportes,
 * como llenar un nuevo reporte, ver reportes previamente realizados y navegar hacia atrás.
 * Se integra con otras actividades para gestionar reportes dentro del sistema.
 */

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
    private var idUser: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_realizar_reporte)
        val btnRealizarReporte = findViewById<ImageButton>(R.id.btnRealizarReporte)
        val btnVerReporte = findViewById<ImageButton>(R.id.btnVerReporte)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)

        idUser = intent.getStringExtra("ID_USUARIO") ?: ""

        btnRealizarReporte.setOnClickListener() {
            val intent = Intent(this, LlenarReporteActivity::class.java)
            intent.putExtra("ID_USUARIO", idUser)
            startActivity(intent)
        }

        btnVerReporte.setOnClickListener() {
            val intent = Intent(this, VerReporteActivity::class.java)
            intent.putExtra("ID_USUARIO", idUser)
            startActivity(intent)
        }

        btnAtras.setOnClickListener() {
            finish()
        }
    }
}