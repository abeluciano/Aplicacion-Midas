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