package com.example.midas.AdapterHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryAdapter(
    private val historyList: MutableList<Transferencia>
) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.render(history)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
