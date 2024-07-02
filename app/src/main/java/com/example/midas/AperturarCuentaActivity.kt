/**
 * Actividad para permitir a un usuario aperturar una nueva cuenta en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite al usuario seleccionar el tipo de moneda (Soles o Dólares) y
 * proporcionar la contraseña para abrir una nueva cuenta. Al presionar el botón "Abrir Cuenta",
 * se verifica la contraseña y se procede con la apertura de la cuenta si es válida.
 * También incluye un botón para navegar hacia atrás al menú principal.
 */

package com.example.midas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.Login.Usuario
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class AperturarCuentaActivity : AppCompatActivity(), Usuario.ActivityCallback {
    private lateinit var usuario: Usuario
    private lateinit var tipoMonedaSpinner: Spinner
    private lateinit var contraseñaEditText: EditText
    private lateinit var abrirCuentaButton: Button
    private lateinit var atras: ImageButton
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var idCuenta:String
    private var idUsuario by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aperturar_cuenta)
        dbHelper = DatabaseHelper(this)
        tipoMonedaSpinner = findViewById(R.id.tipoMonedaSpinner)
        contraseñaEditText = findViewById(R.id.contraseñaEditText)
        abrirCuentaButton = findViewById(R.id.abrirCuentaButton)
        atras = findViewById(R.id.imgbtnAtras)
        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""
        idUsuario = intent.getIntExtra("ID_USUARIO", -1)

        val monedas = arrayOf("Soles", "Dolares")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, monedas)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        tipoMonedaSpinner.adapter = adapter


        abrirCuentaButton.setOnClickListener {

            val tipoMoneda = tipoMonedaSpinner.selectedItem.toString()
            val contraseña = contraseñaEditText.text.toString()

            if (contraseña.isBlank() || contraseña.isEmpty()){
                showInvalidEmailNotification("Debe completar todos los campos")
                return@setOnClickListener
            }

            if (idUsuario != -1) {
                usuario = Usuario(idUsuario,idCuenta, this,this)
            }

            if (::usuario.isInitialized) {
                usuario.abrirCuenta(tipoMoneda, contraseña)
            }

        }
        atras.setOnClickListener(){
            finish()
        }
    }
    override fun onCuentaCreada() {
        finish()
    }
    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_title = customSnackView.findViewById<TextView>(R.id.snackbar_title)
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_title.text = dbHelper.getNombreUsuarioByCuenta(idCuenta)
        snackbar_text.text = msg
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }

}
