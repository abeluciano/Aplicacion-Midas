
/**
 * ViewHolder para una transferencia en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase HistoryViewHolder representa una vista de un solo elemento (Transferencia) en un
 * RecyclerView. Contiene referencias a las vistas dentro de cada elemento y un método
 * para vincular los datos de una transferencia específica a estas vistas. También maneja
 * la visualización y el color del tipo de transferencia (Entrada o Salida).
 */
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
    private val tipoTransferenciaTextView = itemView.findViewById<TextView>(R.id.tipoTransferenciaTextView)
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
