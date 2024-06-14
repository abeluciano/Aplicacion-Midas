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
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
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
