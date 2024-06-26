/**
 * Actividad para visualizar reportes realizados por un usuario en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad muestra todos los reportes realizados por un usuario específico.
 * Utiliza un RecyclerView para mostrar la lista de reportes y permite al usuario
 * navegar hacia atrás para salir de la actividad.
 */

package com.example.midas.Reportes

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterReporte.ReporteAdapter
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Reporte
import com.example.midas.R

class VerReporteActivity : AppCompatActivity() {
    private lateinit var user:String
    private var idUser: String = ""
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var reporteAdapter: ReporteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_reporte)

        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        dbHelper = DatabaseHelper(this)
        idUser = intent.getStringExtra("ID_USUARIO") ?: ""
        user = idUser

        btnAtras.setOnClickListener() {
            finish()
        }

        initRecyclerView()
    }

    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_reporte = dbHelper.getReportesByUsuario(user)
        reporteAdapter = ReporteAdapter(list_reporte){ reporte ->
            onItemSelected(reporte)
        }

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerViewReporte)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = reporteAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    fun onItemSelected(reporte: Reporte) {
        val id = reporte.idReporte
        val tipo = reporte.tipoReporte
        val hora = reporte.FechayHora
        val descripcion = reporte.descripcion
        val estado = reporte.estado
        val respuesta = reporte.respuesta

        if (hora != null && respuesta != null && hora != null) {
            initDialog(id, tipo, hora, descripcion, estado, respuesta)
        }
    }

    private fun initDialog(id: String, tipo: String, hora: String, descripcion: String, estado: String, respuesta: String) {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_reporte)

        val textViewCod = dialog.findViewById<TextView>(R.id.textViewCod)
        val Descripcion = dialog.findViewById<TextView>(R.id.Descripcion)
        val textViewProblema = dialog.findViewById<TextView>(R.id.textViewProblema)
        val textViewFecha = dialog.findViewById<TextView>(R.id.textViewFecha)
        val txtRespuesta = dialog.findViewById<TextView>(R.id.txtRespuesta)
        val buttonAceptar = dialog.findViewById<Button>(R.id.buttonAceptar)


        Descripcion.text = descripcion
        textViewProblema.text= tipo
        textViewCod.text= "Cod: $id"
        textViewFecha.text= hora
        txtRespuesta.text= respuesta

        buttonAceptar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}