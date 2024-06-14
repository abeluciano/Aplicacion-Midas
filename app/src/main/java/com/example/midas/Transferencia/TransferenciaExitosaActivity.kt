package com.example.midas.Transferencia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.MenuActivity
import com.example.midas.R

class TransferenciaExitosaActivity : AppCompatActivity() {

    private var simbolo: String = ""
    private var montoConvertido: String = ""
    private var currentDate: String = ""
    private var currentTime: String = ""
    private var nombreUser: String = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia_exitosa)

        simbolo = intent.getStringExtra("SIMBOLO") ?: ""
        montoConvertido = intent.getStringExtra("MONTO") ?: ""
        currentDate = intent.getStringExtra("FECHA") ?: ""
        currentTime = intent.getStringExtra("HORA") ?: ""
        nombreUser = intent.getStringExtra("NAME") ?: ""

        val simboloMonedaTextView = findViewById<TextView>(R.id.simboloMonedaTextView)
        val txtMonto = findViewById<TextView>(R.id.txtMonto)
        val txtName = findViewById<TextView>(R.id.txtName)
        val TextFechaHora = findViewById<TextView>(R.id.TextFechaHora)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        simboloMonedaTextView.text = simbolo
        txtMonto.text = montoConvertido
        txtName.text = nombreUser
        TextFechaHora.text = "$currentDate" + "    " + "$currentTime"

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}