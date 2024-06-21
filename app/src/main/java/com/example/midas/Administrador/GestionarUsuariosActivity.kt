/**
 * Actividad para gestionar usuarios en la aplicación.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase GestionarUsuariosActivity es una actividad de Android que permite gestionar
 * usuarios en la aplicación. Se conecta a una base de datos mediante DatabaseHelper,
 * inicializa un RecyclerView con un adaptador UsuariosAdapter para mostrar los usuarios
 * y maneja eventos de clic en los elementos de la lista de usuarios.
 */

package com.example.midas.Administrador

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
import com.example.midas.Administrador.AdapterReportes.ReportesAdapter
import com.example.midas.Administrador.AdministradorAdapterUsers.UsuariosAdapter
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class GestionarUsuariosActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var usuariosAdapter: UsuariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_usuarios)
        dbHelper = DatabaseHelper(this)
        val btnAtrasUsers = findViewById<ImageButton>(R.id.btnAtrasUsers)

        btnAtrasUsers.setOnClickListener(){
            finish()
        }

        initRecyclerView()
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getAllUsuarios()
        usuariosAdapter = UsuariosAdapter(list_transfer) { usuario ->
            onItemSelected(usuario)
        }

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.ReyclerUsuarios)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = usuariosAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun onItemSelected(usuarios: Usuarios) {
        Toast.makeText(this, "En proceso", Toast.LENGTH_SHORT).show()
    }
}