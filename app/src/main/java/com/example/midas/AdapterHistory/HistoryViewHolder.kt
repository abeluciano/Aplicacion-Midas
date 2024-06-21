
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

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var dbHelper: DatabaseHelper
    private val accountDesNameTextView = itemView.findViewById<TextView>(R.id.accountDesNameTextView)
    private val transBalanceTextView = itemView.findViewById<TextView>(R.id.transBalanceTextView)
    private val accountDestIdTextView = itemView.findViewById<TextView>(R.id.accountDestIdTextView)

    @SuppressLint("SetTextI18n")
    fun render(item: Transferencia) {

        if (item.tipoTransferencia == "Salida"){
            accountDesNameTextView.text = item.nombreDestino ?: "N/A"
        } else {
            accountDesNameTextView.text = item.nombreOrigen ?: "N/A"
        }

        if (item.tipoTransferencia == "Salida") {
            transBalanceTextView.text = dbHelper.getCurrencyTypeById(item.cuentaOrigen)
        } else {
            transBalanceTextView.text = item.cuentaDestino?.let { dbHelper.getCurrencyTypeById(it) }
        }

        if (item.tipoTransferencia == "Salida") {
            accountDestIdTextView.text = "- ${item.monto}"
            accountDestIdTextView.setTextColor(Color.RED)
        }else {
            accountDestIdTextView.text = "+ ${item.monto}"
            accountDestIdTextView.setTextColor(Color.GREEN)
        }
    }
}
