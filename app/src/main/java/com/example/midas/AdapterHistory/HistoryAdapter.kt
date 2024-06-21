
/**
 * Adaptador para una lista de transferencias en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase HistoryAdapter es un adaptador personalizado para mostrar una lista de objetos
 * Transferencia en un RecyclerView. Se encarga de crear y vincular las vistas para cada
 * transferencia en la lista.
 */
package com.example.midas.AdapterHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.BD.DatabaseHelper


import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryAdapter(
    private val historyList: MutableList<Transferencia>,
    private val dbHelper: DatabaseHelper
) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view, dbHelper)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.render(history)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
