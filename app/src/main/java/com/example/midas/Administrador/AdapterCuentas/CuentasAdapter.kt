package com.example.midas.Administrador.AdapterCuentas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterHistory.HistoryViewHolder
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.DatasClass.Transferencia
import com.example.midas.R

class CuentasAdapter(
    private val cuentasList: MutableList<Cuentas>
) : RecyclerView.Adapter<CuentasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cuentas_item, parent, false)
        return CuentasViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuentasViewHolder, position: Int) {
        val cuentas = cuentasList[position]
        holder.render(cuentas)
    }

    override fun getItemCount(): Int {
        return cuentasList.size
    }
}