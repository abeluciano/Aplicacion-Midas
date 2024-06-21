/**
 * Clase que representa una transferencia de dinero en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase Transferencia es una data class que almacena la información de una transferencia
 * realizada entre cuentas, incluyendo el monto transferido, la fecha, las cuentas de origen y destino,
 * los nombres asociados a esas cuentas (si están disponibles) y el tipo de transferencia ("Salida" o "Entrada").
 */

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

