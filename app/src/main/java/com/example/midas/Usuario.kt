package com.example.midas

class Usuario(val nombre:String,private val contraseña:String) {
//cambiar nombre a aperturar cuenta??
    val cuentas:MutableList<Cuenta> = mutableListOf()

    fun abrirCuenta(tipoMoneda:String, contraseñaU: String){
        if(contraseña==contraseñaU) {
            if (cuentas.size < 20  && tipoMoneda=="Soles" || tipoMoneda == "Dolares"){
                val nuevaCuenta = Cuenta(tipoMoneda)
                cuentas.add(nuevaCuenta)
                print("Se abrio cuenta")
            }
            else {
                print("A llegado al limite de cuentas")
            }
        }
        else {
            print("La contraseña es incorrecta , datos invalidos")
        }
    }
}
