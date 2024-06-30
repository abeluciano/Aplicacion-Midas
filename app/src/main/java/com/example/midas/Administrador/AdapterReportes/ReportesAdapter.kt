
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

import android.widget.Filter
import android.widget.Filterable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.R

class ReportesAdapter(
    private var reportList: MutableList<Reportes>,
    private val onAccountSelected: (Reportes) -> Unit
) : RecyclerView.Adapter<ReportesViewHolder>(), Filterable {

    private var reportListFiltered: MutableList<Reportes> = reportList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reportes_item, parent, false)
        return ReportesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportesViewHolder, position: Int) {
        val reporte = reportListFiltered[position]
        holder.render(reporte)
        holder.itemView.setOnClickListener {
            onAccountSelected(reporte)
        }
    }

    override fun getItemCount(): Int {
        return reportListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                reportListFiltered = if (charString.isEmpty()) {
                    reportList
                } else {
                    reportList.filter {
                        it.idReporte.contains(charString, ignoreCase = true)
                    }.toMutableList()
                }
                val filterResults = FilterResults()
                filterResults.values = reportListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                reportListFiltered = filterResults.values as MutableList<Reportes>
                notifyDataSetChanged()
            }
        }
    }

    fun filterByState(state: String) {
        reportListFiltered = if (state == "Todos") {
            reportList
        } else {
            reportList.filter { it.estado.equals(state, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}
