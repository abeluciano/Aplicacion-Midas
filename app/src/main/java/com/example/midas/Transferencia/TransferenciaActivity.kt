package com.example.midas.Transferencia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.MenuActivity
import com.example.midas.R
import com.google.android.material.snackbar.Snackbar

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
        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        simboloMonedaTextView = findViewById(R.id.simboloMonedaTextView)
        idCuentaTextView = findViewById(R.id.idCuentaTextView)
        continuarButton = findViewById(R.id.btnContinuar)
        montoEditText = findViewById(R.id.montoEditText)
        cuentaDestino = findViewById(R.id.cuentaDes)


        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        tipoMoneda = intent.getStringExtra("TIPO_MONEDA") ?: ""

        idCuentaTextView.text = "$idCuenta"
        simboloMonedaTextView.text = if (tipoMoneda == "Soles") "S/" else "$"

        btnAtras.setOnClickListener() {
            finish()
        }

        continuarButton.setOnClickListener() {

            val montoString = montoEditText.text.toString().trim()
            val saldo = dbHelper.getSaldoByCuenta(idCuenta)
            val idCuentaDestino = cuentaDestino.text.toString()
            if (montoString.toDouble() > saldo!!) {
                showInvalidEmailNotification("El monto supera al saldo disponible")
                return@setOnClickListener
            }
            if (montoString.toDouble() < 0.1) {
                return@setOnClickListener
            }
            if (idCuentaDestino == idCuenta) {
                showInvalidEmailNotification("No puedes transferir a la misma cuenta en la que estas")
                return@setOnClickListener
            }


            if (dbHelper.verifyAccount(idCuentaDestino)) {
                if (montoString.isNotEmpty() && idCuentaDestino.isNotEmpty()) {
                    val monto = montoString.toDoubleOrNull() ?: 0.0
                    val montoMinimo = if (tipoMoneda == "Soles") 5.0 else 2.0
                    val montomaximo = if (tipoMoneda == "Soles") 1000.0 else 300.0
                    if (monto in montoMinimo..montomaximo) {
                        val montoFormatted = String.format("%.2f", monto)
                        if (monto != montoFormatted.toDouble()) {
                            montoEditText.setText(montoFormatted)
                        }
                    }else {
                        return@setOnClickListener
                    }
                    val intent = Intent(this, ConfirmarTransferenciaActivity::class.java)
                    intent.putExtra("ID_CUENTADESTINO", idCuentaDestino)
                    intent.putExtra("MONTO", montoString)
                    intent.putExtra("ID_CUENTA", idCuenta)
                    startActivity(intent)
                    finish()

                } else {
                    showInvalidEmailNotification("Llene todos los campos")
                }
            } else {
                showInvalidEmailNotification("La cuenta de destino no está registrada")
            }

        }
    }
    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_text.text = msg
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }


}