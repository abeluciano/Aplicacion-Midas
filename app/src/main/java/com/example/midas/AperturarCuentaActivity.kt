package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.SQLException


class AperturarCuentaActivity : AppCompatActivity() {
    private lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aperturar_cuenta)

        var tipoMonedaSpinner = findViewById<Spinner>(R.id.tipoMonedaSpinner)
        var contraseñaEditText = findViewById<EditText>(R.id.contraseñaEditText)
        var abrirCuentaButton = findViewById<Button>(R.id.abrirCuentaButton)
        var connecySQL = AccesoDatos()

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("Id_Usuario", -1)

        if (idUsuario != -1) {
            usuario = Usuario(idUsuario)
        }

        val monedas = arrayOf("Soles", "Dolares")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoMonedaSpinner.adapter = adapter

        abrirCuentaButton.setOnClickListener {
            try {
                val tipoMoneda = tipoMonedaSpinner.selectedItem.toString()
                val contraseñaU = contraseñaEditText.text.toString()

                if (::usuario.isInitialized) {
                    usuario.abrirCuenta(tipoMoneda, contraseñaU,connecySQL)
                }

                Toast.makeText(this, "Se abrio la cuenta", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this, "No se pudo Aperturar Cuenta", Toast.LENGTH_SHORT).show()
            }catch (e: SQLException){
                e.printStackTrace()
                Toast.makeText(this, "No se pudo Aperturar Cuenta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}