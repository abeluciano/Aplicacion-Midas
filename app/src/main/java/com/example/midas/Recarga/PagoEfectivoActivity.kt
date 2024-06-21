package com.example.midas.Recarga

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.R

class PagoEfectivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_efectivo)

        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Cuenta recargada con éxito", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}