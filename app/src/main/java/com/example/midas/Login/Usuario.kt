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

import android.content.Context
import android.widget.Toast
import com.example.midas.BD.DatabaseHelper
import java.util.Random

class Usuario(private val idUsuario: Int, private val context: Context) {

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
                } else {
                    Toast.makeText(context, "Tipo de moneda inválido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No se pueden crear más de 20 cuentas", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "La contraseña es incorrecta, datos inválidos", Toast.LENGTH_SHORT).show()
        }
    }



}
