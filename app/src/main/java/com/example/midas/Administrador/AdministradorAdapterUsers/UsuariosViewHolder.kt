/**
 * ViewHolder para un usuario en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase UsuariosViewHolder representa una vista de un solo elemento (Usuarios) en un
 * RecyclerView. Contiene referencias a las vistas dentro de cada elemento y un método
 * para vincular los datos de un usuario específico a estas vistas.
 */

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

        nombreU.text = " ${item.nameUser}"
        idU.text = "${item.idUser}"
    }
}