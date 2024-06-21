/**
 * Clase que representa una cuenta en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase Cuenta es una data class que almacena la información básica de una cuenta,
 * incluyendo el ID de la cuenta, el saldo y el tipo de moneda.
 */

package com.example.midas.DatasClass

data class Cuenta(var idCuenta: String, val saldo: Double, val tipoMoneda: String)