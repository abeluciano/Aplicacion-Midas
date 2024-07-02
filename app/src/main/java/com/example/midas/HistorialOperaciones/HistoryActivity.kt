package com.example.midas.HistorialOperaciones

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.R

class HistoryActivity : AppCompatActivity() {
    private var id_Cuenta= ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        id_Cuenta = intent.getStringExtra("ID_CUENTA") ?: ""

        val atras = findViewById<ImageButton>(R.id.btnAtras)
        val ht = findViewById<TextView>(R.id.historialtransferencias)
        val hr = findViewById<TextView>(R.id.HistorialRecargas)
        val idCuenta = findViewById<TextView>(R.id.idCuenta)

        idCuenta.text = id_Cuenta

        atras.setOnClickListener {
            finish()
        }

        ht.setOnClickListener {
            val intent = Intent(this, HistoryTransferenciaActivity::class.java)
            intent.putExtra("ID_CUENTA", id_Cuenta)
            startActivity(intent)
        }

        hr.setOnClickListener {
            val intent = Intent(this, HistoryRecargaActivity::class.java)
            intent.putExtra("ID_CUENTA", id_Cuenta)
            startActivity(intent)
        }

    }
}