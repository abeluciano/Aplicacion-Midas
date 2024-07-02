package com.example.midas.AdapterHistory

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Recarga
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryReViewHolder(itemView: View, private val dbHelper: DatabaseHelper): RecyclerView.ViewHolder(itemView) {
    private val accountDesNameTextView: TextView = itemView.findViewById(R.id.accountDesNameTextView)
    private val transBalanceTextView: TextView = itemView.findViewById(R.id.transBalanceTextView)
    private val accountDestIdTextView: TextView = itemView.findViewById(R.id.accountDestIdTextView)
    private val tipoTransferenciaTextView: TextView = itemView.findViewById(R.id.tipoTransferenciaTextView)

    fun render(item: Recarga) {
    }
}
