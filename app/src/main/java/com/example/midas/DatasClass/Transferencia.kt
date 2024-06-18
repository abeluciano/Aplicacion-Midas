package com.example.midas.DatasClass

import java.util.Date

data class Transferencia(
    val monto: Double,
    val fecha: String,
    val cuentaOrigen: String,
    val nombreOrigen: String?,
    val cuentaDestino: String?,
    val nombreDestino: String?,
    val tipoTransferencia: String // "Salida" o "Entrada"
)

