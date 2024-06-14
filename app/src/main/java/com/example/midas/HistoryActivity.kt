package com.example.midas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryAdapter
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Transferencia

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

        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        atras.setOnClickListener() {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        initRecyclerView()
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getTransferenciasByCuenta(idCuenta)
        historyAdapter = HistoryAdapter(list_transfer)

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerViewHistory)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = historyAdapter
        usersRecycler.addItemDecoration(decoration)
    }
}