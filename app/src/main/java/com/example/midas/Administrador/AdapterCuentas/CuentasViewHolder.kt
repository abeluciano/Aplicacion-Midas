package com.example.midas.Administrador.AdapterCuentas

import android.app.Dialog
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R

class CuentasViewHolder(itemView: View, private val dbHelper: DatabaseHelper, private val adapter: CuentasAdapter): RecyclerView.ViewHolder(itemView) {

    private val idCuenta = itemView.findViewById<TextView>(R.id.idCuenta)
    private val tipo = itemView.findViewById<TextView>(R.id.tipo)
    private val saldo = itemView.findViewById<TextView>(R.id.saldo)
    private val btnEstado = itemView.findViewById<Button>(R.id.btnEstado)

    fun render(item: Cuentas) {
        idCuenta.text = item.id
        tipo.text = item.tipoCuenta
        saldo.text = item.saldo.toString()
        if (item.estado == "Activa") {
            btnEstado.text = "Congelar"
            btnEstado.setBackgroundColor(Color.RED)
        } else {
            btnEstado.text = "Descongelar"
            btnEstado.setBackgroundColor(Color.BLUE)
        }
        eventos(item)
    }

    private fun eventos(item: Cuentas) {
        btnEstado.setOnClickListener {
            initDialog(item)
        }
    }

    fun initDialog(item: Cuentas){
        val dialog = Dialog(itemView.context)
        dialog.setContentView(R.layout.dialog_congelar)

        val btnAceptar = dialog.findViewById<Button>(R.id.btnok)
        val btnCancelar = dialog.findViewById<Button>(R.id.btnNo)
        val txtAdvertencia = dialog.findViewById<TextView>(R.id.txtAdvertencia)
        val editTextRazon = dialog.findViewById<EditText>(R.id.editTextRazon)

        if (item.estado == "Activa") {
            txtAdvertencia.text = "Desea congelar la cuenta?"
        } else {
            txtAdvertencia.text = "Desea descongelar la cuenta?"
        }

        btnAceptar.setOnClickListener {
            if (editTextRazon.text.isNotEmpty()) {
                val razon = editTextRazon.text.toString()
                val nuevoEstado = if (item.estado == "Activa") "Congelada" else "Activa"
                dbHelper.cambiarEstadoCuenta(item.id, nuevoEstado)
                dbHelper.updateRazonCuenta(item.id, razon)
                item.estado = nuevoEstado
                adapter.notifyItemChanged(adapterPosition)
                dialog.dismiss()
            } else {
                editTextRazon.error = "Campo requerido"
            }
        }
        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
