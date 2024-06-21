/**
 * Actividad para registrar un nuevo usuario en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite al usuario completar el registro proporcionando un ID de usuario,
 * nombre completo, correo electrónico y contraseña. Se realizan validaciones en los campos
 * ingresados antes de proceder con el registro en la base de datos. Las validaciones incluyen
 * longitud del ID de usuario, formato y seguridad de la contraseña, validez del correo electrónico
 * y concordancia de la contraseña confirmada. Si el registro es exitoso, se muestra un mensaje
 * de confirmación y se redirige al usuario a la pantalla de inicio de sesión.
 */

package com.example.midas

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
import com.example.midas.BD.DatabaseHelper
import com.example.midas.Login.MainActivity
import com.google.android.material.snackbar.Snackbar

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
            showInvalidEmailNotification2("Registro exitoso")
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
            showInvalidEmailNotification("El ID de usuario debe tener 8 caracteres")
            return false
        }

        if (!esNombreValido(nombre)) {
            showInvalidEmailNotification("Por favor ingrese nombre y apellido válidos")
            return false
        }

        if (!esCorreoValido(correo)) {
            showInvalidEmailNotification("El correo no tiene un dominio valido")
            return false
        }

        if (!esContraseñaValida(contraseña)) {
            showInvalidEmailNotification("La contraseña no es segura")
            return false
        }

        if (contraseña != reconfirmar) {
            showInvalidEmailNotification("La contraseña no coincide")
            return false
        }

        if (contraseña.length < 8) {
            showInvalidEmailNotification("La contraseña es muy corta")
            return false
        }

        if (dbHelper.isUserExists(idUsuario)) {
            showInvalidEmailNotification("El ID de usuario ya existe")
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
