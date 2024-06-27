package com.example.midas.Administrador.DataClassAdmin

data class Reportes(
    val idReporte: String,
    val tipo: String,
    val descripcion: String,
    val respuesta: String?,
    val estado: String,
    val fecha: String?,
    val hora: String?
)
