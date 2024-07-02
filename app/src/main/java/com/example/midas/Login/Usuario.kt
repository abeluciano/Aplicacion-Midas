/**
 * Clase Usuario que representa un usuario en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase Usuario gestiona las operaciones relacionadas con un usuario específico,
 * como verificar la contraseña, abrir una nueva cuenta bancaria y proporcionar
 * retroalimentación mediante mensajes Toast según el resultado de las operaciones.
 */

package com.example.midas.Login

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.midas.AperturarCuentaActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R
import com.google.android.material.snackbar.Snackbar
import java.util.Random

class Usuario(private val idUsuario: Int, private val idCuenta: String, private val context: Context, private val callback: ActivityCallback) {

    interface ActivityCallback {
        fun onCuentaCreada()
    }

    private val dbHelper = DatabaseHelper(context)

    fun verificarContraseña(contraseña: String): Boolean {
        val contraseñaCorrecta = dbHelper.getUserPassword(idUsuario.toString())
        return contraseña == contraseñaCorrecta
    }

    private fun generarIdCuentaAleatorio(): Int {
        return Random().nextInt(1000000001) + 1000000000
    }

    fun abrirCuenta(tipoMoneda: String, contraseña: String) {
        if (verificarContraseña(contraseña)) {
            val numCuentas = dbHelper.getNumeroCuentasUsuario(idUsuario.toString())

            if (numCuentas < 20) {
                if (tipoMoneda == "Soles" || tipoMoneda == "Dolares") {
                    val nuevaCuentaId = generarIdCuentaAleatorio()
                    dbHelper.addCuenta(nuevaCuentaId, tipoMoneda, idUsuario.toString())
                    Toast.makeText(context, "Se abrió una cuenta con el ID: $nuevaCuentaId", Toast.LENGTH_SHORT).show()
                    callback.onCuentaCreada() // Llamar al callback para finalizar la actividad
                } else {
                    showInvalidEmailNotification("Tipo de moneda inválido")
                }
            } else {
                showInvalidEmailNotification("No se pueden crear más de 20 cuentas")
            }
        } else {
            showInvalidEmailNotification("La contraseña es incorrecta, datos inválidos")
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification(msg: String) {
        val snackbar = Snackbar.make((context as AperturarCuentaActivity).findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = context.layoutInflater.inflate(R.layout.custom_snackbar, null)
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
