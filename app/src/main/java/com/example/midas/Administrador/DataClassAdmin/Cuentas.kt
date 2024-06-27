package com.example.midas.Administrador.DataClassAdmin

data class Cuentas(
    val id: String,
    val saldo: Double,
    var estado: String,
    val tipoCuenta: String
)
