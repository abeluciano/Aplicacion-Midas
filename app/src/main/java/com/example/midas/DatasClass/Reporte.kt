/**
 * Clase que representa un reporte en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase Reporte es una data class que almacena la información básica de un reporte,
 * incluyendo el tipo de reporte, su estado y la fecha y hora en que fue creado.
 */

package com.example.midas.DatasClass

data class Reporte(var tipoReporte: String, val estado: String, val FechayHora: String)
