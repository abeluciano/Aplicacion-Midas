package com.example.midas.Administrador.AdapterCuentas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class CuentasAdapter(private val cuentasList: List<Cuentas>,
                     private val dbHelper: DatabaseHelper,
                     private val onCuentaSelected: (Cuentas) -> Unit
) : RecyclerView.Adapter<CuentasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cuentas_item, parent, false)
        return CuentasViewHolder(view, dbHelper, this)
    }

    override fun onBindViewHolder(holder: CuentasViewHolder, position: Int) {
         val cuentas = (cuentasList[position])
        holder.render(cuentas)
        holder.itemView.setOnClickListener {
            onCuentaSelected(cuentas)
        }
    }

    override fun getItemCount(): Int {
        return cuentasList.size
    }
}
