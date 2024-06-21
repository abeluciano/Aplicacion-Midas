package com.example.midas.Reportes
/**
 * Actividad para llenar un reporte dentro de la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite a los usuarios llenar y enviar un reporte sobre un problema específico
 * que están experimentando. Los usuarios seleccionan el tipo de reporte y proporcionan una descripción
 * detallada del problema. El reporte se guarda en la base de datos con su estado inicial como "no revisado".
 */

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates
import kotlin.random.Random

class LlenarReporteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private var idUser: String = ""
    private lateinit var spinner:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llenar_reporte)

        dbHelper = DatabaseHelper(this)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        val btnReportar = findViewById<Button>(R.id.btnReportar)
        val VerReporte = findViewById<TextView>(R.id.VerReporte)
        val txtField = findViewById<TextInputEditText>(R.id.txtField)
        spinner = findViewById(R.id.spinner)

        idUser = intent.getStringExtra("ID_USUARIO") ?: ""

        val tipoReporte = arrayOf("Problema en mi cuenta",
                "Problema de Transferencia",
                "Problema de Cambio de Moneda",
                "Problema en la aplicación",
                "Otro")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoReporte)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        VerReporte.setOnClickListener() {
            val intent = Intent(this, VerReporteActivity::class.java)
            intent.putExtra("ID_USUARIO", idUser)
            startActivity(intent)
        }

        btnReportar.setOnClickListener() {
            try {
                val tipoSelecReport = spinner.selectedItem.toString()
                val reporteTexto = txtField.text.toString().trim()
                val idReporte = Random.nextInt(100000000, 200000000)
                val estado = "no revisado"
                val respuesta = "ninguna"
                val user = idUser
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

                if (reporteTexto.isBlank()) {
                    showInvalidEmailNotification("Llene la descripcion")
                    return@setOnClickListener
                }

                dbHelper.addReporte(
                    idReporte,
                    tipoSelecReport,
                    reporteTexto,
                    respuesta,
                    estado,
                    currentDate,
                    currentTime,
                    user
                )
                showInvalidEmailNotification2("Reporte Guardado")
                finish()
            }catch (e:Exception) {
                showInvalidEmailNotification("Error al guardar reporte")
            }
        }

        btnAtras.setOnClickListener() {
            finish()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_text.text = msg
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }

    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification2(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar2, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_text.text = msg
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }
}