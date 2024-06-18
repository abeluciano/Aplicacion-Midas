package com.example.midas.AdapterHistory

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val accountDesNameTextView = itemView.findViewById<TextView>(R.id.accountDesNameTextView)
    private val transBalanceTextView = itemView.findViewById<TextView>(R.id.transBalanceTextView)
    private val accountDesIdTextView = itemView.findViewById<TextView>(R.id.accountDesIdTextView)
    private val accountDateTextView = itemView.findViewById<TextView>(R.id.accountDateTextView)
    private val tipoTransferenciaTextView = itemView.findViewById<TextView>(R.id.tipoTransferenciaTextView)
    private val accountOrigNameTextView = itemView.findViewById<TextView>(R.id.accountOrigNameTextView)
    private val accountDestIdTextView = itemView.findViewById<TextView>(R.id.accountDestIdTextView)

    fun render(item: Transferencia) {

        accountDesNameTextView.text = "Nombre Destino: ${item.nombreDestino ?: "N/A"}"
        transBalanceTextView.text = "Monto: ${item.monto}"
        accountDesIdTextView.text = "Cuenta Origen: ${item.cuentaOrigen}"
        accountDateTextView.text = "Fecha: ${item.fecha}"
        accountOrigNameTextView.text = "Nombre Origen: ${item.nombreOrigen ?: "N/A"}"
        accountDestIdTextView.text = "Cuenta Destino: ${item.cuentaDestino ?: "N/A"}"

        if (item.tipoTransferencia == "Salida") {
            tipoTransferenciaTextView.text = "Salida"
            tipoTransferenciaTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorSalida))
        } else {
            tipoTransferenciaTextView.text = "Entrada"
            tipoTransferenciaTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorEntrada))
        }
    }
}
