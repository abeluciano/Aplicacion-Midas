package com.example.midas.AdapterHistory

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val accountDesNameTextView = itemView.findViewById<TextView>(R.id.accountDesNameTextView)
    val transBalanceTextView = itemView.findViewById<TextView>(R.id.transBalanceTextView)
    val accountDesIdTextView = itemView.findViewById<TextView>(R.id.accountDesIdTextView)
    val accountDateTextView = itemView.findViewById<TextView>(R.id.accountDateTextView)

    fun render(item: Transferencia) {
        accountDesNameTextView.text = item.nombreDestino
        transBalanceTextView.text = item.monto.toString()
        accountDesIdTextView.text = item.cuentaOrigen
        accountDateTextView.text = item.fecha
    }
}