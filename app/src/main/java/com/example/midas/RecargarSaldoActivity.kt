package com.example.midas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
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

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recargar_saldo )

        dbHelper = DatabaseHelper(this)

        idCuentaTextView = findViewById(R.id.idCuentaTextView)
        simboloMonedaTextView = findViewById(R.id.simboloMonedaTextView)
        montoEditText = findViewById(R.id.montoEditText)
        continuarButton = findViewById(R.id.continuarButton)
        var regresar = findViewById<ImageButton>(R.id.imgbtnAtrasRe)

        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        tipoMoneda = intent.getStringExtra("TIPO_MONEDA") ?: ""

        idCuentaTextView.text = "ID Cuenta: $idCuenta"
        simboloMonedaTextView.text = if (tipoMoneda == "Soles") "S/" else "$"



        continuarButton.setOnClickListener {
            val montoString = montoEditText.text.toString().trim()

            if (montoString.isNotEmpty()) {
                val monto = montoString.toDoubleOrNull() ?: 0.0
                val montoMinimo = if (tipoMoneda == "Soles") 5.0 else 2.0
                val montomaximo = if (tipoMoneda == "Soles") 4000.0 else 1500.0

                if (monto in montoMinimo..montomaximo) {
                    val montoFormatted = String.format("%.2f", monto)
                    if (monto != montoFormatted.toDouble()) {
                        montoEditText.setText(montoFormatted)
                    }
                    recargarCuenta(idCuenta, monto)
                    Toast.makeText(this, "Cuenta recargada con éxito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (monto < montoMinimo) {
                        Toast.makeText(this, "El monto ingresado es menor al mínimo permitido", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "El monto ingresado ha superado el límite de recarga", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show()
            }
        }

        regresar.setOnClickListener(){
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
