/**
 * Actividad para recargar saldo en una cuenta específica de la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite al usuario ingresar un monto para recargar saldo en una cuenta
 * seleccionada. Se valida que el monto ingresado esté dentro de los límites establecidos
 * según el tipo de moneda (Soles o Dólares). Una vez validado, se realiza la recarga de
 * saldo y se muestra un mensaje de confirmación. También ofrece la opción de regresar atrás
 * si el usuario decide cancelar la operación.
 */

package com.example.midas.Recarga

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
import com.example.midas.AperturarCuentaActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R
import com.google.android.material.snackbar.Snackbar

class RecargarSaldoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var idCuentaTextView: TextView
    private lateinit var simboloMonedaTextView: TextView
    private lateinit var montoEditText: EditText
    private lateinit var continuarButton: Button
    private lateinit var regresar:ImageButton

    private var idCuenta: String = ""
    private var tipoMoneda: String = ""

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recargar_saldo)

        dbHelper = DatabaseHelper(this)

        idCuentaTextView = findViewById(R.id.idCuentaTextView)
        simboloMonedaTextView = findViewById(R.id.simboloMonedaTextView)
        montoEditText = findViewById(R.id.montoEditText)
        continuarButton = findViewById(R.id.continuarButton)
        regresar = findViewById(R.id.imgbtnAtrasRe)

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
                    dbHelper.recargarCuenta(idCuenta, monto)
                    val intent = Intent(this, PagoEfectivoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (monto < montoMinimo) {
                        showInvalidEmailNotification("El monto ingresado es menor al mínimo permitido")
                    } else {
                        showInvalidEmailNotification("El monto ingresado ha superado el límite de recarga")
                    }
                }
            } else {
                Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show()
            }
        }
        regresar.setOnClickListener(){
            finish()
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
