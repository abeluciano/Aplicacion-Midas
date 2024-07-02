package com.example.midas.HistorialOperaciones


import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryAdapter
import com.example.midas.AdapterHistoryRe.HistoryReAdapter
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R


class HistoryRecargaActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var historyAdapter: HistoryReAdapter
    private lateinit var atras: ImageButton
    private var idCuenta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_recarga)
        dbHelper = DatabaseHelper(this)
        atras = findViewById(R.id.btnAtras)
        val txtCuenta = findViewById<TextView>(R.id.idCuentaTextView)

        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        atras.setOnClickListener() {
            finish()
        }
        txtCuenta.text = idCuenta
        initRecyclerView()
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_transfer = dbHelper.getRecargasByIdCuenta(idCuenta)
        historyAdapter = HistoryReAdapter(list_transfer,dbHelper)

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerViewHistory)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = historyAdapter
        usersRecycler.addItemDecoration(decoration)
    }
}