package com.example.midas

class RealizarTransferencia {

    fun transferir(cuentaOrigen:Cuenta, cuentaDestino:Cuenta, monto:Double):Boolean{
        if (cuentaOrigen.tipoMoneda != cuentaDestino.tipoMoneda) {
            print("No se puede transferir entre cuentas de diferente tipo de Moneda")
            return false
        }

        if(cuentaOrigen.saldo >= monto) {
            cuentaOrigen.retirar(monto)
            cuentaDestino.depositar(monto)
            print("Tranferencia exitosa")
            return true
        } else {
            print("La cuenta origen no tiene suficientes fondos para realizar la transferencia")
            return false
        }

    }

}