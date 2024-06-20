/**
 * ViewHolder para un reporte en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase ReportesViewHolder representa una vista de un solo elemento (Reportes) en un
 * RecyclerView. Contiene referencias a las vistas dentro de cada elemento y un método
 * para vincular los datos de un reporte específico a estas vistas.
 */

package com.example.midas.Administrador.AdapterReportes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.R

class ReportesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val idReporte = itemView.findViewById<TextView>(R.id.idReporte)
    private val typeReport = itemView.findViewById<TextView>(R.id.typeReport)
    private val estado = itemView.findViewById<TextView>(R.id.estado)
    private val respuesta = itemView.findViewById<TextView>(R.id.respuesta)
    private val fechayhora = itemView.findViewById<TextView>(R.id.fechayhora)

    fun render(item: Reportes) {
        idReporte.text = "Id:Reporte: ${item.idReporte}"
        typeReport.text = "Tipo Reporte: ${item.tipo}"
        respuesta.text = "ID Usuario Origen: ${item.respuesta}"
        estado.text = "Nombre Usuario: ${item.estado}"
        fechayhora.text = "Fecha: ${item.fecha}    Hora: ${item.hora}"
    }
}