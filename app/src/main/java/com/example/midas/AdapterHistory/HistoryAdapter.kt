package com.example.midas.AdapterHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterAccount.AccountViewHolder

import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryAdapter(
    private val context: Context,
    private val historyList: List<Transferencia>,
    private val onHistorySelected: (Transferencia) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.accountDesNameTextView.text = history.nombreDestino
        holder.transBalanceTextView.text = history.monto.toString()
        holder.accountDesIdTextView.text = history.cuentaOrigen
        holder.accountDateTextView.text = history.fecha

        holder.itemView.setOnClickListener {
            onHistorySelected(history)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
