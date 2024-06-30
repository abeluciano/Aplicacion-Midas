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

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.AdministradorAdapterUsers.UsuariosAdapter
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class GestionarUsuariosActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var usuariosAdapter: UsuariosAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_usuarios)
        dbHelper = DatabaseHelper(this)
        val btnAtrasUsers = findViewById<ImageButton>(R.id.btnAtrasUsers)

        btnAtrasUsers.setOnClickListener {
            finish()
        }

        searchView = findViewById(R.id.searchView)
        initRecyclerView()
        setupSearchView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getAllUsuarios().toMutableList()
        usuariosAdapter = UsuariosAdapter(list_transfer) { usuario ->
            onItemSelected(usuario)
        }

        val decoration = DividerItemDecoration(this, manager.orientation)
        val usersRecycler = findViewById<RecyclerView>(R.id.ReyclerUsuarios)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = usuariosAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                usuariosAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                usuariosAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun onItemSelected(usuarios: Usuarios) {
        val intent = Intent(this, UsuarioSeleccionActivity::class.java)
        intent.putExtra("ID_USARIO", usuarios.idUser)
        intent.putExtra("NOMBRE_USUARIO", usuarios.nameUser)
        startActivity(intent)
    }
}
