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
    private val montoTextView: TextView = itemView.findViewById(R.id.montoTextView)
    private val fechayhora: TextView = itemView.findViewById(R.id.fechayhora)

    fun render(item: Recarga) {
        val simbol = dbHelper.getTipoCuentaByCuenta(item.idCuenta)
        val simbolo = if(simbol == "Soles") "S/." else "$"
        montoTextView.text = "+ $simbolo ${item.monto}"
        montoTextView.setTextColor(Color.GREEN)
        fechayhora.text = "Fecha: ${item.fecha}  Hora: ${item.hora}"
    }
}
