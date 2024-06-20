package com.example.midas.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.Administrador.MenuAdminActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.MenuActivity
import com.example.midas.R
import com.example.midas.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        dbHelper.createDefaultAdministradores()

        val nombre = findViewById<EditText>(R.id.txtUsuario)
        val codigo = findViewById<EditText>(R.id.txtClave)
        val botonEnviar = findViewById<Button>(R.id.btnIniciar)
        val signUpButton = findViewById<TextView>(R.id.btnSignUp)

        botonEnviar.setOnClickListener {
            val userName = nombre.text.toString()
            val password = codigo.text.toString()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                val userId = dbHelper.verifyUser(userName, password)
                val adminId = dbHelper.verifyAdmin(userName, password)

                if (userId != -1 || adminId != -1) {
                    val isAdmin = dbHelper.checkIfAdmin(if (userId != -1) userId.toString() else adminId.toString())
                    Toast.makeText(this, "${dbHelper.hashCode()}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("Id_Usuario", userId)
                    editor.putString("nombre", userName)
                    editor.apply()

                    val intent = if (isAdmin) {
                        Intent(this, MenuAdminActivity::class.java)
                    } else {
                        Intent(this, MenuActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
