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

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.AdapterReportes.ReportesAdapter
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class GestionarReportesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var reportesAdapter: ReportesAdapter
    private lateinit var searchView: SearchView
    private lateinit var spinnerEstado: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_reportes)

        dbHelper = DatabaseHelper(this)
        val imgbtnAtrasRep = findViewById<ImageButton>(R.id.btnAtrasRep)

        imgbtnAtrasRep.setOnClickListener {
            finish()
        }

        searchView = findViewById(R.id.searchView)
        spinnerEstado = findViewById(R.id.spinnerEstado)


        initRecyclerView()
        setupSearchView()
        setupSpinner()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val listTransfer = dbHelper.getAllReportes().toMutableList()
        reportesAdapter = ReportesAdapter(listTransfer) { reporte ->
            onItemSelected(reporte)
        }

        val decoration = DividerItemDecoration(this, manager.orientation)
        val usersRecycler = findViewById<RecyclerView>(R.id.ReyclerReportes)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = reportesAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                reportesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                reportesAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupSpinner() {
        val estados = arrayOf("Todos", "Revisado", "Solucionado", "No revisado")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter

        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val estado = parent.getItemAtPosition(position).toString()
                reportesAdapter.filterByState(estado)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun onItemSelected(reporte: Reportes) {
        val id = reporte.idReporte
        val tipo = reporte.tipo
        val hora = reporte.hora
        val descripcion = reporte.descripcion
        val estado = reporte.estado
        val respuesta = reporte.respuesta
        val fecha = reporte.fecha
        if (hora != null && respuesta != null && fecha != null) {
            initDialog(id, tipo, hora, descripcion, estado, respuesta, fecha)
        }
    }

    private fun initDialog(id: String, tipo: String, hora: String, descripcion: String, estado: String, respuesta: String, fecha: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_cuenta)

        val textViewCod = dialog.findViewById<TextView>(R.id.textViewCod)
        val Descripcion = dialog.findViewById<TextView>(R.id.Descripcion)
        val textViewProblema = dialog.findViewById<TextView>(R.id.textViewProblema)
        val textViewFecha = dialog.findViewById<TextView>(R.id.textViewFecha)
        val textViewDescripcion = dialog.findViewById<EditText>(R.id.textViewDescripcion)
        val spinnerEstado = dialog.findViewById<Spinner>(R.id.spinnerEstado)
        val buttonAceptar = dialog.findViewById<Button>(R.id.buttonAceptar)

        val estdo = arrayOf("Revisado", "Solucionado", "No revisado")
        val adapter = ArrayAdapter(this, R.layout.spinner, estdo)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerEstado.adapter = adapter
        Descripcion.text = descripcion
        textViewProblema.text = tipo
        textViewCod.text = "Cod: $id"
        textViewFecha.text = "Fecha: $fecha, Hora: $hora"

        buttonAceptar.setOnClickListener {
            val respuestaActulizada = textViewDescripcion.text.toString()
            val estadoActulizado = spinnerEstado.selectedItem.toString()
            if (respuestaActulizada.isNotEmpty() && estadoActulizado.isNotEmpty()) {
                val actualizado = dbHelper.actualizarReporte(id, estadoActulizado, respuestaActulizada)
                if (actualizado) {
                    Toast.makeText(this, "Reporte actualizado", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    initRecyclerView()
                } else {
                    Toast.makeText(this, "Error al actualizar el reporte", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
