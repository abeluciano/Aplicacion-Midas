/**
 * Adaptador para una lista de reportes en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase ReporteAdapter es un adaptador personalizado para mostrar una lista de objetos
 * Reporte en un RecyclerView. Se encarga de crear y vincular las vistas para cada reporte en
 * la lista.
 */

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