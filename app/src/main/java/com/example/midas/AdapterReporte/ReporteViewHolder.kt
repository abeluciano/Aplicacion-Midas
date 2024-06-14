package com.example.midas.AdapterReporte

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Reporte
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class ReporteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val typeReport = itemView.findViewById<TextView>(R.id.typeReport)
    val estado = itemView.findViewById<TextView>(R.id.estado)
    val FechayHora = itemView.findViewById<TextView>(R.id.FechayHora)

    fun render(item: Reporte) {
        typeReport.text = item.tipoReporte
        estado.text = item.estado
        FechayHora.text = item.FechayHora
    }
}