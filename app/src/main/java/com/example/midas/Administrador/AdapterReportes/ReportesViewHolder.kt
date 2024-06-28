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

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.R

class ReportesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val idReporte = itemView.findViewById<TextView>(R.id.idReporte)
    private val typeReport = itemView.findViewById<TextView>(R.id.typeReport)
    private val estado = itemView.findViewById<TextView>(R.id.estado)
    private val respuesta = itemView.findViewById<TextView>(R.id.respuesta)
    private val fechayhora = itemView.findViewById<TextView>(R.id.fechayhora)
    private val btnCopiar = itemView.findViewById<ImageButton>(R.id.btnCopiar)

    fun render(item: Reportes) {
        idReporte.text = "${item.idReporte}"
        typeReport.text = " ${item.tipo}"
        respuesta.text = "${item.respuesta}"
        if(item.estado == "Solucionado") {
            estado.text = item.estado
            estado.setTextColor(Color.GREEN)
        } else {
            estado.text = item.estado
            estado.setTextColor(Color.RED)
        }
        fechayhora.text = "Fecha: ${item.fecha}    Hora: ${item.hora}"

        btnCopiar.setOnClickListener(){
            val clipboard = ContextCompat.getSystemService(
                itemView.context,
                ClipboardManager::class.java
            ) as ClipboardManager
            val clip = ClipData.newPlainText("ID", item.idReporte)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(itemView.context, "ID copiado al portapapeles", Toast.LENGTH_SHORT).show()
        }
    }
}