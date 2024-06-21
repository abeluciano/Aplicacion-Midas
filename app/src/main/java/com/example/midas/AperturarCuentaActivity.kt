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

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.Login.Usuario

class AperturarCuentaActivity : AppCompatActivity() {
    private lateinit var usuario: Usuario
    private lateinit var tipoMonedaSpinner: Spinner
    private lateinit var contraseñaEditText: EditText
    private lateinit var abrirCuentaButton: Button
    private lateinit var atras: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aperturar_cuenta)

        tipoMonedaSpinner = findViewById(R.id.tipoMonedaSpinner)
        contraseñaEditText = findViewById(R.id.contraseñaEditText)
        abrirCuentaButton = findViewById(R.id.abrirCuentaButton)
        atras = findViewById(R.id.imgbtnAtras)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("Id_Usuario", -1)

        if (idUsuario != -1) {
            usuario = Usuario(idUsuario, this)
        }

        val monedas = arrayOf("Soles", "Dolares")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoMonedaSpinner.adapter = adapter

        abrirCuentaButton.setOnClickListener {
            try {
                val tipoMoneda = tipoMonedaSpinner.selectedItem.toString()
                val contraseña = contraseñaEditText.text.toString()

                if (::usuario.isInitialized) {
                    usuario.abrirCuenta(tipoMoneda, contraseña)
                }
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "No se pudo aperturar cuenta", Toast.LENGTH_SHORT).show()
            }
        }
        atras.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
