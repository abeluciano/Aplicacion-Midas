package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val AbrirCuenta = findViewById<Button>(R.id.btnAperturarCuenta)
        val CambiodeMoneda = findViewById<Button>(R.id.btnCambioMoneda)
        val Transferencia = findViewById<Button>(R.id.btnTransferencia)

        AbrirCuenta.setOnClickListener(){
            val intent = Intent(this, AperturarCuentaActivity::class.java)
            startActivity(intent)
        }
        CambiodeMoneda.setOnClickListener(){
            val intent = Intent(this, CambioMonedaActivity::class.java)
            startActivity(intent)
        }

        Transferencia.setOnClickListener(){
            val intent = Intent(this, TransferenciaActivity::class.java)
            startActivity(intent)
        }

    }
}