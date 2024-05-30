package com.example.midas

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
            if (tipoMoneda == "Soles" || tipoMoneda == "Dolares") {
                val nuevaCuentaId = generarIdCuentaAleatorio()
                dbHelper.addCuenta(nuevaCuentaId, tipoMoneda, idUsuario.toString())
                Toast.makeText(context, "\"Se abrió una cuenta con el ID: $nuevaCuentaId", Toast.LENGTH_SHORT).show()
            } else {
                println("Tipo de moneda inválido")
            }
        } else {
            println("La contraseña es incorrecta, datos inválidos")
        }
    }



}
