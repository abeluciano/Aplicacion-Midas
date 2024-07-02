package com.example.midas.AdapterHistory

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryViewHolder(itemView: View, private val dbHelper: DatabaseHelper): RecyclerView.ViewHolder(itemView) {
    private val accountDesNameTextView: TextView = itemView.findViewById(R.id.accountDesNameTextView)
    private val transBalanceTextView: TextView = itemView.findViewById(R.id.transBalanceTextView)
    private val accountDestIdTextView: TextView = itemView.findViewById(R.id.accountDestIdTextView)
    private val tipoTransferenciaTextView: TextView = itemView.findViewById(R.id.tipoTransferenciaTextView)

    @SuppressLint("SetTextI18n")
    fun render(item: Transferencia) {
        val nombreCuenta = if (item.tipoTransferencia == "Salida") {
            item.nombreDestino ?: "N/A"
        } else {
            item.nombreOrigen ?: "N/A"
        }
        accountDesNameTextView.text = " $nombreCuenta"

        val moneda = if (item.tipoTransferencia == "Salida") {
            dbHelper.getCurrencyTypeById(item.cuentaDestino)
        } else {
            dbHelper.getCurrencyTypeById(item.cuentaDestino)
        }

        transBalanceTextView.text = moneda

        val monto = if (item.tipoTransferencia == "Salida") {
            "- ${item.monto}"
        } else {
            "+ ${item.monto}"
        }

        accountDestIdTextView.text = monto
        accountDestIdTextView.setTextColor(
            if (item.tipoTransferencia == "Salida") Color.RED else Color.GREEN
        )

        tipoTransferenciaTextView.text = "Fecha: ${item.fecha}"
    }
}
