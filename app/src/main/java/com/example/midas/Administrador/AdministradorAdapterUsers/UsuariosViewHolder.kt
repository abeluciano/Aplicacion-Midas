package com.example.midas.Administrador.AdministradorAdapterUsers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.R

class UsuariosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val nombreU = itemView.findViewById<TextView>(R.id.nombreU)
    private val idU = itemView.findViewById<TextView>(R.id.idU)

    fun render(item: Usuarios) {

        nombreU.text = "Nombre Usuario: ${item.nameUser}"
        idU.text = "ID Usuario: ${item.idUser}"
    }
}