package com.example.midas.Transferencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.DatabaseHelper
import com.example.midas.MenuActivity
import com.example.midas.R

class TransferenciaActivity : AppCompatActivity() {

    private lateinit var simboloMonedaTextView: TextView
    private lateinit var idCuentaTextView: TextView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var continuarButton: Button
    private lateinit var montoEditText:EditText
    private lateinit var cuentaDestino:EditText
    private var tipoMoneda: String = ""
    private var idCuenta: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia)

        dbHelper = DatabaseHelper(this)
        var btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        simboloMonedaTextView = findViewById(R.id.simboloMonedaTextView)
        idCuentaTextView = findViewById(R.id.idCuentaTextView)
        continuarButton = findViewById(R.id.btnContinuar)
        montoEditText = findViewById(R.id.montoEditText)
        cuentaDestino = findViewById(R.id.cuentaDes)


        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        tipoMoneda = intent.getStringExtra("TIPO_MONEDA") ?: ""

        idCuentaTextView.text = "ID Cuenta: $idCuenta"
        simboloMonedaTextView.text = if (tipoMoneda == "Soles") "S/" else "$"

        btnAtras.setOnClickListener() {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        continuarButton.setOnClickListener(){
            val montoString = montoEditText.text.toString().trim()
            val idCuentaDestino = cuentaDestino.text
            if (dbHelper.verifyAccount(idCuentaDestino.toString())) {
                if (montoString.isNotEmpty() && idCuentaDestino.isNotEmpty()) {
                    val monto = montoString.toDoubleOrNull() ?: 0.0
                    val montoMinimo = if (tipoMoneda == "Soles") 5.0 else 2.0
                    val montomaximo = if (tipoMoneda == "Soles") 1000.0 else 300.0

                    if (monto in montoMinimo..montomaximo) {
                        val montoFormatted = String.format("%.2f", monto)
                        if (monto != montoFormatted.toDouble()) {
                            montoEditText.setText(montoFormatted)
                        }
                    }
                    val intent = Intent(this, algoaqui::class.java)
                    intent.putExtra("ID_CUENTADESTINO", idCuentaDestino)
                    intent.putExtra("MONTO", montoString)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "La cuenta de destino no está registrada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}