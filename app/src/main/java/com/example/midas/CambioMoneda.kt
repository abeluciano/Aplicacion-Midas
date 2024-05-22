package com.example.midas

class CambioMoneda {
    private val tipoCambio = 3.75

    fun cambiarSolesADolares(cuentaSoles:Cuenta, cuentaDolares:Cuenta, montoSoles:Double):Boolean {
        if (cuentaSoles.saldo >= montoSoles){
            val montoDolares = montoSoles/tipoCambio
            cuentaSoles.retirar(montoSoles)
            cuentaDolares.depositar(montoDolares)
            return true
        }else {
            println("no fondos")
            return false
        }
    }

    fun cambiarDolaresASoles(cuentaDolares: Cuenta, cuentaSoles: Cuenta, montoDolares:Double):Boolean {
        if (cuentaDolares.saldo >=montoDolares) {
            val montoSoles = montoDolares * tipoCambio
            cuentaDolares.retirar(montoDolares)
            cuentaSoles.depositar(montoSoles)
            return true
        }else {
            println("no fondos")
            return false
        }
    }
}