package com.example.midas
import android.util.Log
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Random

class Usuario(private  val idUsuario: Int) {
    fun verificarContraseña(contraseña:String, connecySQL:AccesoDatos): Boolean {

        var contraseñaC = false
        try {
            val statement: PreparedStatement = connecySQL.dbConn()?.prepareStatement(
                "SELECT contraseña FROM Usuario WHERE Id_Usuario = $idUsuario"
            )!!
            //statement.setInt(1, idUsuario)
            val resultSet: ResultSet = statement.executeQuery()
            Log.d("pepito", "$statement")
            if (resultSet.next()) {
                val contraseñaBD = resultSet.getString("contraseña")
                contraseñaC = contraseña == contraseñaBD
                Log.d("pepito", "$contraseñaC")
            }
        } catch (ex: SQLException) {
            Log.d("pepito","Error al verificar la contraseña: ${ex.message}")
            contraseñaC = false
        }
        return contraseñaC
    }
    private fun generarIdCuentaAleatorio(): Int {
        return Random().nextInt(1000000001) + 1000000000
    }
    fun abrirCuenta(
        tipoMoneda: String,
        contraseñaU: String,
        connecySQL: AccesoDatos
    ) {
        if (verificarContraseña(contraseñaU,connecySQL)) {
            if (tipoMoneda == "Soles" || tipoMoneda == "Dolares") {
                try {

                    val nuevaCuentaId = generarIdCuentaAleatorio()
                    val nuevaCuenta: PreparedStatement = connecySQL.dbConn()?.prepareStatement(
                        "INSERT INTO Cuenta (Id_Cuenta, Tipo_Cuenta, Saldo, Estado_Cuenta, Id_Usuario) VALUES (?, ?, 00.00, 'Activa', ?)"
                    )!!
                    Log.d("pepito", "$nuevaCuenta")
                    nuevaCuenta.setInt(1, nuevaCuentaId)
                    nuevaCuenta.setString(2, tipoMoneda)
                    nuevaCuenta.setInt(3, idUsuario)
                    nuevaCuenta.executeUpdate()
                    val mensaje= "Se creo Cuenta"
                    imprimir(mensaje)
                } catch (ex: SQLException) {
                    val mensaje = "Error al abrir Cuenta: ${ex.message}"
                    imprimir(mensaje)
                }
            } else {
                val mensaje = "Tipo de moneda inválido"
                imprimir(mensaje)
            }
        } else {
            val mensaje = "La contraseña es incorrecta, datos inválidos"
            imprimir(mensaje)
        }
    }
    fun imprimir(msg:String): String {

        return msg
    }

}




