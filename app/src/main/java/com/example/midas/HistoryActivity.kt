/**
 * Actividad para mostrar el historial de transferencias de una cuenta en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad muestra el historial de transferencias asociadas a una cuenta específica.
 * Utiliza un RecyclerView para desplegar las transferencias en una lista, con un adaptador
 * personalizado (HistoryAdapter). También muestra el nombre del usuario dueño de la cuenta
 * y permite regresar al menú principal al presionar el botón "Atrás".
 */

package com.example.midas


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryAdapter
import com.example.midas.BD.DatabaseHelper


class HistoryActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var atras: ImageButton
    private var idCuenta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        dbHelper = DatabaseHelper(this)
        atras = findViewById(R.id.btnAtras)
        //val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val txtCuenta = findViewById<TextView>(R.id.idCuentaTextView)

        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        val nameUser = dbHelper.getNombreUsuarioByCuenta(idCuenta)
        atras.setOnClickListener() {
            finish()
        }
        txtCuenta.text = idCuenta
        //txtUsuario.text = nameUser
        initRecyclerView()
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getTransferenciasByCuenta(idCuenta)
        historyAdapter = HistoryAdapter(list_transfer,dbHelper)

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerViewHistory)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = historyAdapter
        usersRecycler.addItemDecoration(decoration)
    }
}