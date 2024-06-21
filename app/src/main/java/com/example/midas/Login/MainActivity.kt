/**
 * Actividad principal que gestiona el inicio de sesión en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * MainActivity es la actividad principal de inicio de sesión de la aplicación Midas.
 * Permite a los usuarios ingresar su nombre de usuario y contraseña, verifica las credenciales
 * utilizando la base de datos local y redirige a la actividad correspondiente según si el usuario es
 * administrador o no. También ofrece la opción de registrarse para nuevos usuarios.
 */

package com.example.midas.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.google.android.material.snackbar.Snackbar

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
                    showInvalidEmailNotification2("Login exitoso")
                    val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("Id_Usuario", userId)
                    editor.putInt("Id_Admin", adminId)
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
                    showInvalidEmailNotification("Usuario o contraseña incorrectos")
                }
            } else {
                showInvalidEmailNotification("Por favor ingrese todos los campos")
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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

    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification2(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar2, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_text.text = msg
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }
}

