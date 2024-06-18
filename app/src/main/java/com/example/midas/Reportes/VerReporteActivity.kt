package com.example.midas.Reportes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterReporte.ReporteAdapter
import com.example.midas.BD.DatabaseHelper
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
        reporteAdapter = ReporteAdapter(list_reporte)

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerViewReporte)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = reporteAdapter
        usersRecycler.addItemDecoration(decoration)
    }
}