package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecargarSaldoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var idCuentaTextView: TextView
    private lateinit var simboloMonedaTextView: TextView
    private lateinit var montoEditText: EditText
    private lateinit var continuarButton: Button

    private var idCuenta: String = ""
    private var tipoMoneda: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recargar_saldo )

        dbHelper = DatabaseHelper(this)

        idCuentaTextView = findViewById(R.id.idCuentaTextView)
        simboloMonedaTextView = findViewById(R.id.simboloMonedaTextView)
        montoEditText = findViewById(R.id.montoEditText)
        continuarButton = findViewById(R.id.continuarButton)
        var recargar = findViewById<ImageButton>(R.id.imgbtnAtrasRe)

        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        tipoMoneda = intent.getStringExtra("TIPO_MONEDA") ?: ""

        idCuentaTextView.text = "ID Cuenta: $idCuenta"
        simboloMonedaTextView.text = if (tipoMoneda == "Soles") "S/" else "$"

        continuarButton.setOnClickListener {
            val montoString = montoEditText.text.toString()

            if (montoString.isNotEmpty()) {
                try {
                    val monto = montoString.toDouble()
                    val montoMinimo = if (tipoMoneda == "Soles") 5.0 else 2.0

                    if (monto > montoMinimo) {
                        recargarCuenta(idCuenta, monto)
                        Toast.makeText(this, "Cuenta recargada con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show()
            }
        }
        recargar.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun recargarCuenta(idCuenta: String, monto: Double) {
        val db = dbHelper.writableDatabase
        val query = "UPDATE Cuenta SET Saldo = Saldo + ? WHERE Id_Cuenta = ?"
        val statement = db.compileStatement(query)
        statement.bindDouble(1, monto)
        statement.bindString(2, idCuenta)
        statement.executeUpdateDelete()
    }
}
