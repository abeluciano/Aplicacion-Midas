package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.Login.MainActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DatabaseHelper(this)

        val idUsuarioEditText = findViewById<EditText>(R.id.idUsuarioEditText)
        val nombreEditText = findViewById<EditText>(R.id.nombreEditText)
        val correoEditText = findViewById<EditText>(R.id.correoEditText)
        val contraseñaEditText = findViewById<EditText>(R.id.contraseñaEditText)
        val confirmarContraseña = findViewById<EditText>(R.id.etxtConfirmarContraseña)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val imgbtnAtras = findViewById<ImageButton>(R.id.imgbtnAtrasR)

        signUpButton.setOnClickListener {
            val idUsuario = idUsuarioEditText.text.toString()
            val nombre = nombreEditText.text.toString()
            val correo = correoEditText.text.toString()
            val contraseña = contraseñaEditText.text.toString()
            val reconfirmar = confirmarContraseña.text.toString()

            if (!validarCampos(idUsuario, nombre, correo, contraseña, reconfirmar)) {
                return@setOnClickListener
            }

            dbHelper.addUser(idUsuario, normalizarNombre(nombre), contraseña, correo)
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        imgbtnAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validarCampos(idUsuario: String, nombre: String, correo: String, contraseña: String, reconfirmar: String): Boolean {
        if (idUsuario.length != 8 || idUsuario.isEmpty()) {
            Toast.makeText(this, "El ID de usuario debe tener 8 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!esNombreValido(nombre)) {
            Toast.makeText(this, "Por favor ingrese nombre y apellido válidos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!esCorreoValido(correo)) {
            Toast.makeText(this, "El correo no tiene un dominio valido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!esContraseñaValida(contraseña)) {
            Toast.makeText(this, "La contraseña no es segura", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contraseña != reconfirmar) {
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contraseña.length < 8) {
            Toast.makeText(this, "La contraseña es muy corta", Toast.LENGTH_SHORT).show()
            return false
        }

        if (dbHelper.isUserExists(idUsuario)) {
            Toast.makeText(this, "El ID de usuario ya existe", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun normalizarNombre(nombre: String): String {
        return nombre.split(" ").joinToString(" ") { palabra ->
            palabra.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    private fun esNombreValido(nombre: String): Boolean {
        val partes = nombre.split(" ")
        if (partes.size < 2) {
            return false
        }
        for (parte in partes) {
            if (parte.length < 3) {
                return false
            }
        }
        return true
    }

    private fun esContraseñaValida(contraseña: String): Boolean {
        val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}$"
        return contraseña.matches(regex.toRegex())
    }

    private fun esCorreoValido(correo: String): Boolean {
        val regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"
        return correo.matches(regex.toRegex())
    }
}
