package com.example.midas.AdapterReporte

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Reporte

import com.example.midas.R

class ReporteAdapter( private val reporteList: MutableList<Reporte>
) : RecyclerView.Adapter<ReporteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reporte_item, parent, false)
        return ReporteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val reporte = reporteList[position]
        holder.render(reporte)
    }

    override fun getItemCount(): Int {
        return reporteList.size
    }
}