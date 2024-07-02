package com.example.midas.AdapterHistoryRe

import com.example.midas.AdapterHistory.HistoryReViewHolder
import com.example.midas.DatasClass.Recarga
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.BD.DatabaseHelper

import com.example.midas.R

class HistoryReAdapter(
    private val historyList: MutableList<Recarga>,
    private val dbHelper: DatabaseHelper
) : RecyclerView.Adapter<HistoryReViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryReViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryReViewHolder(view, dbHelper)
    }

    override fun onBindViewHolder(holder: HistoryReViewHolder, position: Int) {
        val historyRe = historyList[position]
        holder.render(historyRe)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}