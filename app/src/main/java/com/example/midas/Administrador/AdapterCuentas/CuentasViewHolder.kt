package com.example.midas.Administrador.AdapterCuentas

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class CuentasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var dbHelper: DatabaseHelper
    private val accountDesNameTextView = itemView.findViewById<TextView>(R.id.accountDesNameTextView)
    private val transBalanceTextView = itemView.findViewById<TextView>(R.id.transBalanceTextView)
    private val accountDestIdTextView = itemView.findViewById<TextView>(R.id.accountDestIdTextView)

    fun render(item: Cuentas) {


    }
}