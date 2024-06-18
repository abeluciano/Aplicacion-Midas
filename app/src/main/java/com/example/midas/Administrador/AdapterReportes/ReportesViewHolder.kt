package com.example.midas.Administrador.AdapterReportes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.R

class ReportesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val typeReport = itemView.findViewById<TextView>(R.id.typeReport)
    private val nombre = itemView.findViewById<TextView>(R.id.nombre)
    private val idUser = itemView.findViewById<TextView>(R.id.idUser)
    private val fechayhora = itemView.findViewById<TextView>(R.id.fechayhora)

    fun render(item: Reportes) {

        typeReport.text = "Tipo Reporte: ${item.tipo}"
        nombre.text = "Nombre Usuario: ${item.nombreUser}"
        idUser.text = "ID Usuario Origen: ${item.idUser}"
        fechayhora.text = "Fecha: ${item.fecha}    Hora: ${item.hora}"
    }
}