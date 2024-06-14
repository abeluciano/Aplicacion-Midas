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

            if (idUsuario.length == 8 && idUsuario.isNotEmpty() && nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty()) {
                if (esCorreoValido(correo)) {
                    if (esContraseñaValida(contraseña)) {
                        if ( contraseña == reconfirmar){
                            if (contraseña.length >= 8){
                                if (!dbHelper.isUserExists(idUsuario)) {
                                    dbHelper.addUser(idUsuario, nombre, contraseña, correo)
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "El ID de usuario ya existe", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this, "La contraseña es muy corta", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "La contraseña no es segura", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "El correo no tiene un dominio valido", Toast.LENGTH_LONG).show()
                }
            } else {
                    Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
        imgbtnAtras.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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
