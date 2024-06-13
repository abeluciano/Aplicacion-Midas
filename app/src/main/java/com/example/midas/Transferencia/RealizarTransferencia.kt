package com.example.midas.Transferencia

import android.util.Log
import java.sql.PreparedStatement
import java.sql.ResultSet

class RealizarTransferencia(private  val idUsuario: Int) {
/*
    fun obtenerSaldo(connecySQL: AccesoDatos):Double {
        var saldo = 00.00
        val statement: PreparedStatement = connecySQL.dbConn()?.prepareStatement(
            "SELECT Saldo FROM Cuenta WHERE Id_Usuario = $idUsuario"
        )!!
        val resultSet: ResultSet = statement.executeQuery()
        if (resultSet.next()) {
            saldo = resultSet.getDouble("Saldo")
            return saldo
        }else {
            Log.d("pepito", "Error al verificar el saldo")
            return saldo
        }

    }

    fun transferir(tipoMoneda:String, cuentaOrigen:Int, cuentaDestino:Int, monto:Double,connecySQL:AccesoDatos):Boolean{
        var saldo = obtenerSaldo(connecySQL)
        = obtenerSaldo(connecySQL)
        if (tipoMoneda == ) {
            print("No se puede transferir entre cuentas de diferente tipo de Moneda")
            return false
        }
        if(saldo >= monto) {
            retirar(monto,connecySQL)
            depositar(monto,connecySQL)
            print("Tranferencia exitosa")
            return true
        } else {
            print("La cuenta origen no tiene suficientes fondos para realizar la transferencia")
            return false
        }

    }
    fun retirar(monto:Double, connecySQL:AccesoDatos) {
        val statement: PreparedStatement = connecySQL.dbConn()?.prepareStatement(
            "SELECT Saldo FROM Cuenta WHERE Id_Usuario = $idUsuario"
        )!!

        if (monto > 0 && saldo >= monto){
            saldo -= monto
        } else {
            println("Error no tiene los fondos para retirar")
        }
    }

    fun depositar(monto: Double, connecySQL:AccesoDatos){
        if (monto > 0) {
            saldo += monto
        } else {
            println("La candidad debe ser mayor a 0")
        }
    }*/

}