package com.example.midas

import kotlin.random.Random

class Cuenta(val tipoMoneda: String ) {

    var saldo: Double = 0.0
        private set
    fun retirar(cantidad:Double) {
        if (cantidad > 0 && saldo >= cantidad){
            saldo -= cantidad
        } else {
            println("Error no tiene los fondos para retirar")
        }
    }

    fun depositar(cantidad: Double){
        if (cantidad > 0) {
            saldo += cantidad
        } else {
            println("La candidad debe ser mayor a 0")
        }
    }

}