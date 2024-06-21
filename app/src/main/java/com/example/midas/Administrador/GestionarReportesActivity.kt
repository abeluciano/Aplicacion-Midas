/**
 * Actividad para gestionar reportes en la aplicación.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase GestionarReportesActivity es una actividad de Android que permite gestionar
 * reportes en la aplicación. Se conecta a una base de datos mediante DatabaseHelper,
 * inicializa un RecyclerView con un adaptador ReportesAdapter para mostrar los reportes
 * y maneja eventos de clic en los elementos de la lista de reportes. También tiene una
 * función para mostrar un diálogo.
 */

package com.example.midas.Administrador

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryAdapter
import com.example.midas.Administrador.AdapterReportes.ReportesAdapter
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Cuenta
import com.example.midas.R

class GestionarReportesActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var reportesAdapter: ReportesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_reportes)
        dbHelper = DatabaseHelper(this)
        val imgbtnAtrasRep = findViewById<ImageButton>(R.id.btnAtrasRep)

        imgbtnAtrasRep.setOnClickListener(){
            finish()
        }

        initRecyclerView()
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getAllReportes()
        reportesAdapter = ReportesAdapter(list_transfer) { reporte ->
            onItemSelected(reporte)
        }

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.ReyclerReportes)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = reportesAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun onItemSelected(reporte: Reportes) {

        Toast.makeText(this, "En proceso", Toast.LENGTH_SHORT).show()
    }

    fun initDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_cuenta)
        dialog.show()
    }
}
