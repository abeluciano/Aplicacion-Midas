package com.example.midas.Administrador

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryAdapter
import com.example.midas.Administrador.AdapterCuentas.CuentasAdapter
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class UsuarioSeleccionActivity : AppCompatActivity() {
    private var idUsuar: String = ""
    private var nameUsuar: String = ""
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var cuentasAdapter: CuentasAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_seleccion)

        dbHelper = DatabaseHelper(this)
        val nameUSer = findViewById<TextView>(R.id.nameUSer)
        val btnRetroceder = findViewById<ImageButton>(R.id.btnRetroceder)

        idUsuar = intent.getStringExtra("ID_USARIO") ?: ""
        nameUsuar = intent.getStringExtra("NOMBRE_USUARIO") ?: ""
        nameUSer.text = nameUsuar

        btnRetroceder.setOnClickListener {
            finish()
        }
        initRecyclerView()
    }

    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getCuentasByUsuario2(idUsuar)
        cuentasAdapter = CuentasAdapter(list_transfer, dbHelper) { cuentas ->
            onItemSelected(cuentas)
        }

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.ReyclerCuentas)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = cuentasAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun onItemSelected(cuentas: Cuentas) {
    }
}
