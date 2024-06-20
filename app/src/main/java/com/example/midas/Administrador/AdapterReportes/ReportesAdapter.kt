
/**
 * Adaptador para una lista de reportes en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase ReportesAdapter es un adaptador personalizado para mostrar una lista de objetos
 * Reportes en un RecyclerView. Se encarga de crear y vincular las vistas para cada reporte en
 * la lista y maneja los eventos de clic en cada elemento para seleccionar un reporte.
 */
package com.example.midas.Administrador.AdapterReportes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Reportes


import com.example.midas.R

class ReportesAdapter(private val reportList: MutableList<Reportes>,
                      private val onAccountSelected: (Reportes) -> Unit
) : RecyclerView.Adapter<ReportesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reportes_item, parent, false)
        return ReportesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportesViewHolder, position: Int) {
        val reporte = reportList[position]
        holder.render(reporte)
        holder.itemView.setOnClickListener {
            onAccountSelected(reporte)
        }
    }

    override fun getItemCount(): Int {
        return reportList.size
    }
}