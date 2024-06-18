package com.example.midas.Administrador.AdministradorAdapterUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.R

class UsuariosAdapter (private val usersList: MutableList<Usuarios>,
                       private val onAccountSelected: (Usuarios) -> Unit
) : RecyclerView.Adapter<UsuariosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        return UsuariosViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        val usuarios = usersList[position]
        holder.render(usuarios)
        holder.itemView.setOnClickListener {
            onAccountSelected(usuarios)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}